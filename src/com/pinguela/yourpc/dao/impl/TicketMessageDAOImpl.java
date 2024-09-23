package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.util.JDBCUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class TicketMessageDAOImpl 
extends AbstractDAO<TicketMessage>
implements TicketMessageDAO {	

	private static final String SELECT_COLUMNS =
			" SELECT tm.ID,"
					+ " tm.TICKET_ID,"
					+ " tm.CUSTOMER_ID,"
					+ " tm.EMPLOYEE_ID,"
					+ " tm.DATE,"
					+ " tm.TEXT";
	private static final String FROM_TABLE =
			" FROM TICKET_MESSAGE tm";
	private static final String WHERE_TICKET_ID =
			" WHERE tm.TICKET_ID = ?";
	private static final String FINDBYTICKET_QUERY = SELECT_COLUMNS +FROM_TABLE +WHERE_TICKET_ID;

	private static final String CREATE_QUERY =
			" INSERT INTO TICKET_MESSAGE"
					+ "(TICKET_ID,"
					+ " CUSTOMER_ID,"
					+ " EMPLOYEE_ID,"
					+ " DATE,"
					+ " TEXT)"
					+ " VALUES"
					+ " (?, ?, ?, ?, ?)";

	private static final String DELETE_FROM_TABLE =
			" DELETE FROM TICKET_MESSAGE";
	private static final String DELETE_WHERE_ID =
			" WHERE ID = ?";
	private static final String DELETE_WHERE_TICKET_ID =
			" WHERE TICKET_ID = ?";

	private static final String DELETE_QUERY = DELETE_FROM_TABLE +DELETE_WHERE_ID;
	private static final String DELETE_BY_TICKET_QUERY = DELETE_FROM_TABLE +DELETE_WHERE_TICKET_ID;

	private static Logger logger = LogManager.getLogger(TicketMessageDAOImpl.class);
	
	private CustomerDAO customerDAO;
	private EmployeeDAO employeeDAO;
	
	public TicketMessageDAOImpl() {
		super(TicketMessage.class);
		customerDAO = new CustomerDAOImpl();
		employeeDAO = new EmployeeDAOImpl();
	}

	@Override
	public List<TicketMessage> findByTicket(Session session, Long ticketId) 
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<TicketMessage> query = builder.createQuery(getTargetClass());
			Root<TicketMessage> root = query.from(getTargetClass());
			
			Join<TicketMessage, Ticket> joinTicket = root.join("ticket");
			query.where(builder.equal(joinTicket.get("id"), ticketId));
			
			return session.createQuery(query).getResultList();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<TicketMessage> query,
			Root<TicketMessage> root, AbstractCriteria<TicketMessage> criteria) {}

	@Override
	public Long create(Session session, TicketMessage ticketMessage)
			throws DataException {
		return (Long) super.persist(session, ticketMessage);
	}

	@Override
	public Boolean delete(Connection conn, Long messageId) throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_QUERY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, messageId);
			return stmt.executeUpdate() == 1 ? true : false;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public Boolean deleteByTicket(Connection conn, Long ticketId) throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_BY_TICKET_QUERY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, ticketId);
			return stmt.executeUpdate() > 0 ? true : false;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}
}
