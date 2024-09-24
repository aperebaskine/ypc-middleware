package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.model.RMAState;
import com.pinguela.yourpc.model.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class RMADAOImpl 
extends AbstractDAO<Long, RMA>
implements RMADAO {

	private static final String SELECT_COLUMNS = 
			" SELECT r.ID,"
					+ " r.CUSTOMER_ID,"
					+ " r.RMA_STATE_ID,"
					+ " r.CREATION_DATE,"
					+ " r.TRACKING_NUMBER";
	private static final String FROM_TABLE =
			" FROM RMA r";
	private static final String JOIN_CUSTOMER =
			" INNER JOIN CUSTOMER c"
					+ " ON r.CUSTOMER_ID = c.ID";
	private static final String JOIN_ORDER_LINE =
			" INNER JOIN RMA_ORDER_LINE rol"
					+ " ON rol.RMA_ID = r.ID"
					+ " INNER JOIN ORDER_LINE ol"
					+ " ON rol.ORDER_LINE_ID = ol.ID";
	private static final String JOIN_TICKET_ORDER_LINE =
			" INNER JOIN TICKET_ORDER_LINE tol"
					+ " ON ol.ID = tol.ORDER_LINE_ID";
	private static final String GROUP_BY_RMA_ID =
			" GROUP BY r.ID";
	private static final String WHERE_ID = " WHERE r.ID = ?";

	private static final String FINDBY_QUERY =
			SELECT_COLUMNS +FROM_TABLE;
	private static final String FINDBYID_QUERY =
			FINDBY_QUERY +WHERE_ID;
	
	private static final String CREATE_QUERY =
			" INSERT INTO RMA(CUSTOMER_ID, RMA_STATE_ID, CREATION_DATE, TRACKING_NUMBER)"
			+ " VALUES (?, ?, ?, ?)";
	private static final String UPDATE_QUERY = 
			" UPDATE RMA "
			+ "SET CUSTOMER_ID = ?,"
			+ " RMA_STATE_ID = ?,"
			+ " CREATION_DATE = ?,"
			+ " TRACKING_NUMBER = ?"
			+ " WHERE ID = ?";
	
	private static Logger logger = LogManager.getLogger(RMADAOImpl.class);
	private OrderLineDAO orderLineDAO = null;
	
	public RMADAOImpl() {
		orderLineDAO = new OrderLineDAOImpl();
	}

	@Override
	public RMA findById(Session session, Long rmaId) 
			throws DataException {
		return super.findById(session, rmaId);
	}

	@Override
	public List<RMA> findBy(Session session, RMACriteria criteria) throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<RMA> query, Root<RMA> root,
			AbstractCriteria<RMA> criteria) {
		
		RMACriteria rmaCriteria = (RMACriteria) criteria;
		
		Join<RMA, OrderLine> orderLineJoin = null;
		boolean groupBy = false;
		
		if (rmaCriteria.getCustomerId() != null) {
			query.where(builder.equal(root.get("customer").get("id"), rmaCriteria.getCustomerId()));
		}
		if (rmaCriteria.getCustomerEmail() != null) {
			Join<RMA, Customer> joinCustomer = root.join("customer");
			joinCustomer.on(builder.equal(joinCustomer.get("email"), rmaCriteria.getCustomerEmail()));
		}
		if (rmaCriteria.getState() != null) {
			Join<RMA, RMAState> joinState = root.join("state");
			query.where(builder.equal(joinState.get("id"), rmaCriteria.getState()));
		}
		if (rmaCriteria.getOrderId() != null) {
			groupBy = true;
			orderLineJoin = root.join("orderLines");
			orderLineJoin.on(builder.equal(
					orderLineJoin.get("customerOrder").get("id"), rmaCriteria.getOrderId()));
		}
		if (rmaCriteria.getTicketId() != null) {
			groupBy = true;
			if (orderLineJoin == null) {
				orderLineJoin = root.join("orderLines");
			}
			Join<OrderLine, Ticket> joinTicket = orderLineJoin.join("ticket");
			joinTicket.on(builder.equal(joinTicket.get("id"), rmaCriteria.getTicketId()));
		}
		if (rmaCriteria.getMinDate() != null) {
			query.where(builder.greaterThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMinDate()));
		}
		if (rmaCriteria.getMaxDate() != null) {
			query.where(builder.lessThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMaxDate()));
		}
		
		if (groupBy) {
			query.groupBy(root.get("id"));
		}
	}

	@Override
	public Long create(Session session, RMA rma) throws DataException {
		return super.create(session, rma);
	}

	@Override
	public Boolean update(Connection conn, RMA rma) throws DataException {
		
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UPDATE_QUERY);
			int i = setInsertValues(stmt, rma);
			stmt.setLong(i++, rma.getId());
			
			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			} else {
				if (!orderLineDAO.assignToRMA(conn, rma)) {
					return false;
				} else {
					return true;
				}
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		}
	}
	
	private int setInsertValues(PreparedStatement stmt, RMA rma) 
			throws SQLException {
		
		int i = 1;
		
		stmt.setInt(i++, rma.getCustomerId());
		stmt.setString(i++, rma.getState());
		stmt.setTimestamp(i++, new java.sql.Timestamp(rma.getCreationDate().getTime()));
		stmt.setString(i++, rma.getTrackingNumber());
		return i;
	}

}
