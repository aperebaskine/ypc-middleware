package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.OrderLineCriteria;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrderLineDAOImpl 
extends AbstractMutableDAO<Long, OrderLine>
implements OrderLineDAO {

	private static final String ASSIGN_TO_TICKET_QUERY =
			" INSERT INTO TICKET_ORDER_LINE(TICKET_ID, ORDER_LINE_ID, QUANTITY)";
	private static final String ASSIGN_TO_RMA_QUERY =
			" INSERT INTO RMA_ORDER_LINE(RMA_ID, ORDER_LINE_ID, QUANTITY)";

	private static final String UNASSIGN_FROM_TICKET_QUERY =
			" DELETE FROM TICKET_ORDER_LINE WHERE TICKET_ID = ?";
	private static final String UNASSIGN_FROM_RMA_QUERY =
			" DELETE FROM RMA_ORDER_LINE WHERE RMA_ID = ?";

	private static final int ASSIGN_QUERY_COLUMN_COUNT = 3;

	private static Logger logger = LogManager.getLogger(OrderLineDAOImpl.class);

	public OrderLineDAOImpl() {
	}

	@Override
	public List<OrderLine> findByCustomerOrder(Session session, long orderId) 
			throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setOrderId(orderId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<OrderLine> findByTicket(Session session, long ticketId) throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setTicketId(ticketId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<OrderLine> findByRMA(Session session, long rmaId) throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setRmaId(rmaId);
		return super.findBy(session, criteria);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<OrderLine> root, AbstractCriteria<OrderLine> criteria) {
	    OrderLineCriteria orderLineCriteria = (OrderLineCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (orderLineCriteria.getOrderId() != null) {
	        predicates.add(builder.equal(root.get("order").get("id"), orderLineCriteria.getOrderId()));
	    }

	    if (orderLineCriteria.getTicketId() != null) {
	        Join<OrderLine, Ticket> joinTicket = root.join("ticket");
	        joinTicket.on(builder.equal(joinTicket.get("ticketId"), orderLineCriteria.getTicketId()));
	    }

	    if (orderLineCriteria.getRmaId() != null) {
	        Join<OrderLine, Ticket> joinRma = root.join("rma");
	        joinRma.on(builder.equal(joinRma.get("rmaId"), orderLineCriteria.getRmaId()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<OrderLine> query, Root<OrderLine> root,
			AbstractCriteria<OrderLine> criteria) {
		// Unused	
	}

	@Override
	public Boolean create(Session session, List<OrderLine> olList) 
			throws DataException {
		List<Long> identifiers = super.createBatch(session, olList);
		return identifiers.size() == olList.size();
	}

	@Override
	public Boolean deleteByCustomerOrder(Session session, long orderId) 
			throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setOrderId(orderId);
		return super.deleteBy(session, criteria);
	}

	@Override
	public Boolean assignToTicket(Session session, Ticket t) 
			throws DataException {
		unassign(conn, UNASSIGN_FROM_TICKET_QUERY, t.getId());

		String query = new StringBuilder(ASSIGN_TO_TICKET_QUERY)
				.append(SQLQueryUtils.buildPlaceholderValuesClause(t.getOrderLines().size(), ASSIGN_QUERY_COLUMN_COUNT))
				.toString();

		PreparedStatement stmt = null;	

		try {
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for (OrderLine ol : t.getOrderLines()) {
				stmt.setLong(i++, t.getId());
				stmt.setLong(i++, ol.getId());
				stmt.setShort(i++, ol.getQuantity());
			}

			int affectedRows = stmt.executeUpdate();
			return affectedRows == t.getOrderLines().size();
		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		}
	}

	@Override
	public Boolean assignToRMA(Session session, RMA r)
			throws DataException {

		unassign(conn, UNASSIGN_FROM_RMA_QUERY, r.getId());

		String query = new StringBuilder(ASSIGN_TO_RMA_QUERY)
				.append(SQLQueryUtils.buildPlaceholderValuesClause(r.getOrderLines().size(), ASSIGN_QUERY_COLUMN_COUNT))
				.toString();

		PreparedStatement stmt = null;	

		try {
			stmt = conn.prepareStatement(query.toString());
			int i = 1;
			for (OrderLine ol : r.getOrderLines()) {
				stmt.setLong(i++, r.getId());
				stmt.setLong(i++, ol.getId());
				stmt.setShort(i++, ol.getQuantity());
			}

			int affectedRows = stmt.executeUpdate();
			return affectedRows == r.getOrderLines().size();
		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		}
	}

	private void unassign(Connection conn, String formattedQuery, Long key)
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(formattedQuery);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, key);
			stmt.executeUpdate();

		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(stmt);
		}		
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<OrderLine> updateQuery, Root<OrderLine> root,
			AbstractUpdateValues<OrderLine> updateValues) {
		// Unused	
	}

}
