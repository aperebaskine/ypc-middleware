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
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class RMADAOImpl 
implements RMADAO {

	public RMADAOImpl() {
		orderLineDAO = new OrderLineDAOImpl();
	}

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

	@Override
	public RMA findById(Connection conn, Long rmaId) 
			throws DataException {
	
		if (rmaId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		RMA r = null;

		try {
			stmt = conn.prepareStatement(
					FINDBYID_QUERY,
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY
					);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, rmaId);
			rs = stmt.executeQuery();
			if (rs.next()) r = loadNext(conn, rs);
			return r;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public List<RMA> findBy(Connection conn, RMACriteria criteria) throws DataException {

		StringBuilder query = new StringBuilder(FINDBY_QUERY);
		if (criteria.getCustomerEmail() != null) query.append(JOIN_CUSTOMER);
		if (criteria.getOrderId() != null || criteria.getTicketId() != null) query.append(JOIN_ORDER_LINE);
		if (criteria.getTicketId() != null) query.append(JOIN_TICKET_ORDER_LINE);
		query.append(buildWhereClause(criteria)).append(SQLQueryUtils.buildOrderByClause(criteria));

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					query.toString(),
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY
					);
			setSelectValues(stmt, criteria);

			rs = stmt.executeQuery();
			List<RMA> results = new ArrayList<RMA>();
			while (rs.next()) results.add(loadNext(conn, rs));
			return results;
		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		}
	}

	private StringBuilder buildWhereClause(RMACriteria criteria) {

		List<String> clauses = new ArrayList<String>();
		boolean groupBy = false;

		if (criteria.getCustomerId() != null) {
			clauses.add(" r.CUSTOMER_ID = ?");
		}
		if (criteria.getCustomerEmail() != null) {
			clauses.add(" c.EMAIL = ?");
		}
		if (criteria.getState() != null) {
			clauses.add(" r.RMA_STATE_ID = ?");
		}
		if (criteria.getOrderId() != null) {
			groupBy = true;
			clauses.add(" ol.CUSTOMER_ORDER_ID <= ?");
		}
		if (criteria.getTicketId() != null) {
			groupBy = true;
			clauses.add(" tol.TICKET_ID = ?");
		}
		if (criteria.getMinDate() != null) {
			clauses.add(" r.CREATION_DATE >= ?");
		}
		if (criteria.getMaxDate() != null) {
			clauses.add(" r.CREATION_DATE <= ?");
		}
		return SQLQueryUtils.buildWhereClause(clauses).append(groupBy?GROUP_BY_RMA_ID:"");
	}

	private int setSelectValues(PreparedStatement stmt, RMACriteria criteria) 
			throws SQLException {

		int i = 1;

		if (criteria.getCustomerId() != null) {
			stmt.setLong(i++, criteria.getCustomerId());
		}
		if (criteria.getCustomerEmail() != null) {
			stmt.setString(i++, criteria.getCustomerEmail());
		}
		if (criteria.getState() != null) {
			stmt.setString(i++, criteria.getState());
		}
		if (criteria.getOrderId() != null) {
			stmt.setLong(i++, criteria.getOrderId());
		}
		if (criteria.getTicketId() != null) {
			stmt.setLong(i++, criteria.getTicketId());
		}
		if (criteria.getMinDate() != null) {
			stmt.setTimestamp(i++, new java.sql.Timestamp(criteria.getMinDate().getTime()));
		}
		if (criteria.getMaxDate() != null) {
			stmt.setTimestamp(i++, new java.sql.Timestamp(criteria.getMaxDate().getTime()));
		}
		return i;
	}

	private RMA loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		RMA r = new RMA();
		int i = 1;
		r.setId(rs.getLong(i++));
		r.setCustomerId(rs.getInt(i++));
		r.setState(rs.getString(i++));
		r.setCreationDate(rs.getTimestamp(i++));
		r.setTrackingNumber(rs.getString(i++));
		r.setOrderLines(orderLineDAO.findByRMA(conn, r.getId(), null));
		return r;
	}

	@Override
	public Long create(Connection conn, RMA rma) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			rma.setCreationDate(new Date());
			setInsertValues(stmt, rma);
			
			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.first();
				rma.setId(rs.getLong(JDBCUtils.GENERATED_KEY_INDEX));
				return rma.getId();
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		}
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
