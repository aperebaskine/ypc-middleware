package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.LogMessages;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class TicketDAOImpl
implements TicketDAO {

	private static final String SELECT_COLUMNS =
			" SELECT t.ID,"
					+ " t.CUSTOMER_ID,"
					+ " t.EMPLOYEE_ID,"
					+ " t.CREATION_DATE,"
					+ " t.TICKET_STATE_ID,"
					+ " t.TICKET_TYPE_ID,"
					+ " t.PRODUCT_ID,"
					+ " t.TITLE,"
					+ " t.DESCRIPTION";
	private static final String FROM_TABLE =
			" FROM TICKET t"
			+ " INNER JOIN CUSTOMER c"
			+ " ON t.CUSTOMER_ID = c.ID AND c.DELETION_DATE IS NULL";
	private static final String ID_FILTER =
			" WHERE t.ID = ?";

	private static final String FINDBY_QUERY = SELECT_COLUMNS +FROM_TABLE;
	private static final String FINDBYID_QUERY = FINDBY_QUERY +ID_FILTER;

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
	public Ticket findById(Connection conn, Long ticketId) 
			throws DataException {

		if (ticketId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Ticket t = null;

		try {
			stmt = conn.prepareStatement(FINDBYID_QUERY, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, ticketId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				t = loadNext(conn, rs);
			}
			return t;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}

	}

	@Override
	public Results<Ticket> findBy(Connection conn, TicketCriteria criteria, int pos, int pageSize)
			throws DataException {

		StringBuilder query = new StringBuilder(FINDBY_QUERY).append(buildWhereClause(criteria));

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Results<Ticket> results = null;

		try {
			stmt = conn.prepareStatement(
					query.toString(), 
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY
					);
			setSelectValues(stmt, criteria);
			rs = stmt.executeQuery();
			results = loadResults(conn, rs, pos, pageSize);
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}

	}

	private static StringBuilder buildWhereClause(TicketCriteria criteria) {

		List<String> conditions = new ArrayList<String>();

		if (criteria.getCustomerId() != null) {
			conditions.add(" t.CUSTOMER_ID = ?");
		}
		if (criteria.getCustomerEmail() != null) {
			conditions.add(" c.EMAIL = ?");
		}
		if (criteria.getMinDate() != null) {
			conditions.add(" t.CREATION_DATE >= ?");
		}
		if (criteria.getMaxDate() != null) {
			conditions.add(" t.CREATION_DATE <= ?");
		}
		if (criteria.getState() != null) {
			conditions.add(" t.TICKET_STATE_ID = ?");
		}
		if (criteria.getType() != null) {
			conditions.add(" t.TICKET_TYPE_ID = ?");
		}

		return SQLQueryUtils.buildWhereClause(conditions);
	}

	private static int setSelectValues(PreparedStatement stmt, TicketCriteria criteria) 
			throws SQLException {

		int i = 1;

		if (criteria.getCustomerId() != null) {
			stmt.setLong(i++, criteria.getCustomerId());
		}
		if (criteria.getCustomerEmail() != null) {
			stmt.setString(i++, criteria.getCustomerEmail().toLowerCase());
		}
		if (criteria.getMinDate() != null) {
			stmt.setTimestamp(i++, new java.sql.Timestamp(criteria.getMinDate().getTime()));
		}
		if (criteria.getMaxDate() != null) {
			stmt.setTimestamp(i++, new java.sql.Timestamp(criteria.getMaxDate().getTime()));
		}
		if (criteria.getState() != null) {
			stmt.setString(i++, criteria.getState());
		}
		if (criteria.getType() != null) {
			stmt.setString(i++, criteria.getType());
		}

		return i;
	}

	@Override
	public Long create(Connection conn, Ticket ticket) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ticket.setCreationDate(new Date());
			setInsertValues(stmt, ticket);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.first();
				Long id = rs.getLong(JDBCUtils.GENERATED_KEY_INDEX);
				ticket.setId(id);
				insertMessages(conn, ticket);
				return id;
			}

		} catch (SQLException sqle) {
			logger.error("Error al insertar " +ticket, sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}
	
	@Override
	public Boolean update(Connection conn, Ticket ticket) throws DataException {

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

	private Results<Ticket> loadResults(Connection conn, ResultSet rs, int startPos, int pageSize)
			throws SQLException, DataException {

		Results<Ticket> results = new Results<Ticket>();
		results.setResultCount(JDBCUtils.getRowCount(rs));

		if (results.getResultCount() != 0 && startPos>0) {
			int count = 0;
			rs.absolute(startPos);
			do {
				results.getPage().add(loadNext(conn, rs));
				count++;
			} while (count<pageSize && rs.next());	
		}

		return results;
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
