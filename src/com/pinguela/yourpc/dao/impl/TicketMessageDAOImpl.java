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
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketMessageDAOImpl 
implements TicketMessageDAO {	

	private static final String SELECT_COLUMNS =
			" SELECT tm.ID,"
					+ " tm.TICKET_ID,"
					+ " tm.CUSTOMER_ID,"
					+ " tm.EMPLOYEE_ID,"
					+ " tm.DATE,"
					+ " tm.TEXT";
	private static final String FROM_TABLE =
			" FROM TICKET_MESSAGE tm"
			+ " INNER JOIN CUSTOMER c on tm.CUSTOMER_ID = c.ID AND c.DELETION_DATE IS NULL";
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
		customerDAO = new CustomerDAOImpl();
		employeeDAO = new EmployeeDAOImpl();
	}

	@Override
	public List<TicketMessage> findByTicket(Connection conn, Long ticketId) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(FINDBYTICKET_QUERY,
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, ticketId);
			rs = stmt.executeQuery();
			return loadResults(conn, rs);
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private List<TicketMessage> loadResults(Connection conn, ResultSet rs)
			throws SQLException, DataException {

		List<TicketMessage> results = new ArrayList<TicketMessage>();
		while (rs.next()) {
			results.add(loadNext(conn, rs));
		}
		return results;
	}

	private TicketMessage loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		TicketMessage tm = new TicketMessage();
		int i = 1;

		tm.setId(rs.getLong(i++));
		tm.setTicketId(rs.getLong(i++));
		tm.setCustomerId(JDBCUtils.getNullableInt(rs, i++));
		tm.setEmployeeId(JDBCUtils.getNullableInt(rs, i++));
		tm.setTimestamp(rs.getTimestamp(i++));
		tm.setText(rs.getString(i++));
		
		if (tm.getCustomerId() != null) {
			Customer c = customerDAO.findById(conn, tm.getCustomerId());
			tm.setFirstName(c.getFirstName());
			tm.setLastName1(c.getLastName1());
			tm.setLastName2(c.getLastName2());
		} else {
			Employee e = employeeDAO.findById(conn, tm.getEmployeeId());
			tm.setFirstName(e.getFirstName());
			tm.setLastName1(e.getLastName1());
			tm.setLastName2(e.getLastName2());
		}
		
		return tm;
	}

	@Override
	public Long create(Connection conn, TicketMessage ticketMessage)
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			if (ticketMessage.getTimestamp() == null) {
				ticketMessage.setTimestamp(new Date());
			}
			setInsertValues(stmt, ticketMessage);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				logger.error(LogMessages.INSERT_FAILED, ticketMessage);
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.first();
				Long id = rs.getLong(JDBCUtils.GENERATED_KEY_INDEX);
				ticketMessage.setId(id);
				return id;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private static int setInsertValues(PreparedStatement stmt, TicketMessage ticketMessage) 
			throws SQLException {

		int i = 1;
		stmt.setLong(i++, ticketMessage.getTicketId());
		JDBCUtils.setNullable(stmt, ticketMessage.getCustomerId(), i++);
		JDBCUtils.setNullable(stmt, ticketMessage.getEmployeeId(), i++);
		stmt.setTimestamp(i++, new java.sql.Timestamp(ticketMessage.getTimestamp().getTime()));
		stmt.setString(i++, ticketMessage.getText());

		return i;

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
