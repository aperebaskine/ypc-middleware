package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.LogMessages;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.model.TicketState;
import com.pinguela.yourpc.model.TicketType;
import com.pinguela.yourpc.util.JDBCUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class TicketDAOImpl
extends AbstractDAO<Long, Ticket>
implements TicketDAO {

	private static final String CREATE_QUERY =
			" INSERT INTO TICKET(CUSTOMER_ID,"
					+ " EMPLOYEE_ID,"
					+ " CREATION_DATE,"
					+ " TICKET_STATE_ID,"
					+ " TICKET_TYPE_ID,"
					+ " PRODUCT_ID,"
					+ " TITLE,"
					+ " DESCRIPTION)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY = 
			" UPDATE TICKET SET CUSTOMER_ID = ?,"
					+ " EMPLOYEE_ID = ?,"
					+ " CREATION_DATE = ?,"
					+ " TICKET_STATE_ID = ?,"
					+ " TICKET_TYPE_ID = ?,"
					+ " PRODUCT_ID = ?,"
					+ " TITLE = ?,"
					+ " DESCRIPTION = ?"
					+ " WHERE ID = ?";

	private static Logger logger = LogManager.getLogger(TicketDAOImpl.class);
	private TicketMessageDAO ticketMessageDAO = null;
	private OrderLineDAO orderLineDAO = null;

	public TicketDAOImpl() {
		ticketMessageDAO = new TicketMessageDAOImpl();
		orderLineDAO = new OrderLineDAOImpl();
	}

	@Override
	public Ticket findById(Session session, Long ticketId) 
			throws DataException {
		return super.findById(session, ticketId);
	}

	@Override
	public Results<Ticket> findBy(Session session, TicketCriteria ticketCriteria, int pos, int pageSize)
			throws DataException {
		return super.findBy(session, ticketCriteria, pos, pageSize);
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Ticket> query, Root<Ticket> root,
			AbstractCriteria<Ticket> criteria) {
		TicketCriteria ticketCriteria = (TicketCriteria) criteria;
		
		if (ticketCriteria.getCustomerId() != null) {
			query.where(builder.equal(root.get("customer"), ticketCriteria.getCustomerId()));
		}
		if (ticketCriteria.getCustomerEmail() != null) {
			Join<Ticket, Customer> customerJoin = root.join("customer", JoinType.INNER);
			query.where(builder.equal(customerJoin.get("email"), ticketCriteria.getCustomerEmail()));
		}
		if (ticketCriteria.getMinDate() != null) {
			query.where(builder.greaterThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMinDate()));
		}
		if (ticketCriteria.getMaxDate() != null) {
			query.where(builder.lessThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMaxDate()));
		}
		if (ticketCriteria.getState() != null) {
			Join<Ticket, TicketState> ticketStateJoin = root.join("state", JoinType.INNER);
			query.where(builder.equal(ticketStateJoin.get("id"), ticketCriteria.getState()));
		}
		if (ticketCriteria.getType() != null) {
			Join<Ticket, TicketType> ticketTypeJoin = root.join("type", JoinType.INNER);
			query.where(builder.equal(ticketTypeJoin.get("id"), ticketCriteria.getType()));
		}
	}

	@Override
	public Long create(Session session, Ticket ticket) 
			throws DataException {
		return super.create(session, ticket);
	}
	
	@Override
	public Boolean update(Session session, Ticket ticket) throws DataException {

		PreparedStatement stmt = null;

		try {
			ticketMessageDAO.deleteByTicket(conn, ticket.getId()); // Prepare table for re-insertion
			
			stmt = conn.prepareStatement(UPDATE_QUERY);
			int i = setInsertValues(stmt, ticket);
			stmt.setLong(i++, ticket.getId());
			
			int updatedRows = stmt.executeUpdate();
			if (updatedRows != 1) {
				logger.error(LogMessages.UPDATE_FAILED);
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			} else {
				insertMessages(conn, ticket);
				return true;
			}

		} catch (SQLException sqle) {
			logger.error("Error al insertar " +ticket, sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private int setInsertValues(PreparedStatement stmt, Ticket t) 
			throws SQLException {

		int index = 1;
		JDBCUtils.setNullable(stmt, t.getCustomerId(), index++);
		JDBCUtils.setNullable(stmt, t.getEmployeeId(), index++);
		stmt.setTimestamp(index++, new java.sql.Timestamp(t.getCreationDate().getTime()));
		stmt.setString(index++, t.getState());
		stmt.setString(index++, t.getType());
		JDBCUtils.setNullable(stmt, t.getProductId(), index++);
		stmt.setString(index++, t.getTitle());
		JDBCUtils.setNullable(stmt, t.getDescription(), index++);
		return index;
	}

	private void insertMessages(Connection conn, Ticket ticket) 
			throws DataException {
		for (TicketMessage tm : ticket.getMessageList()) {
			tm.setTicketId(ticket.getId());
			ticketMessageDAO.create(conn, tm);
		}
	}

	private Ticket loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		Ticket t = new Ticket();
		int i = 1;

		t.setId(rs.getLong(i++));
		t.setCustomerId(JDBCUtils.getNullableInt(rs, i++));
		t.setEmployeeId(JDBCUtils.getNullableInt(rs, i++));
		t.setCreationDate(rs.getTimestamp(i++));
		t.setState(rs.getString(i++));
		t.setType(rs.getString(i++));
		t.setProductId(rs.getLong(i++));
		t.setTitle(rs.getString(i++));
		t.setDescription(rs.getString(i++));
		t.setMessageList(ticketMessageDAO.findByTicket(conn, t.getId()));
		t.setOrderLines(orderLineDAO.findByTicket(conn, t.getId()));
		return t;
	}

}
