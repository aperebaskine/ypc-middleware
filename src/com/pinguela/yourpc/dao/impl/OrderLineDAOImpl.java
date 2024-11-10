package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class OrderLineDAOImpl implements OrderLineDAO {

	private static final String ORDER_LINE_ALIAS = "ol";
	private static final String TICKET_ORDER_LINE_ALIAS = "tol";
	private static final String RMA_ORDER_LINE_ALIAS = "rol";
	private static final String PRODUCT_LOCALE_ALIAS = "pl";

	// Quantity column table alias is a placeholder to be set in the formatted query constants
	private static final String SELECT_COLUMNS =
			" SELECT " +ORDER_LINE_ALIAS +".ID,"
					+ " " +ORDER_LINE_ALIAS +".CUSTOMER_ORDER_ID,"
					+ " " +ORDER_LINE_ALIAS +".PRODUCT_ID,"
					+ " " +PRODUCT_LOCALE_ALIAS +".NAME,"
					+ " %1$s.QUANTITY,"
					+ " " +ORDER_LINE_ALIAS +".PURCHASE_PRICE,"
					+ " " +ORDER_LINE_ALIAS +".SALE_PRICE";
	private static final String FROM_TABLE =
			" FROM ORDER_LINE ol"
					+ " INNER JOIN PRODUCT p"
					+ " ON ol.PRODUCT_ID = p.ID";
	private static final String JOIN_PRODUCT_LOCALE = 
			" INNER JOIN PRODUCT_LOCALE pl"
					+ " ON pl.PRODUCT_ID = p.ID"
					+ " AND pl.LOCALE_ID = ?";
	private static final String JOIN_TICKET_ORDER_LINE =
			" INNER JOIN TICKET_ORDER_LINE tol"
					+ " ON ol.ID = tol.ORDER_LINE_ID";
	private static final String JOIN_RMA_ORDER_LINE =
			" INNER JOIN RMA_ORDER_LINE rol"
					+ " ON ol.ID = rol.ORDER_LINE_ID";
	private static final String WHERE_CUSTOMER_ORDER_ID =
			" WHERE ol.CUSTOMER_ORDER_ID = ?";
	private static final String WHERE_TICKET_ID =
			" WHERE tol.TICKET_ID = ?";
	private static final String WHERE_RMA_ID =
			" WHERE rol.RMA_ID = ?";

	// Formatted queries with the quantity column table alias set
	private static final String FIND_BY_CUSTOMER_ORDER_QUERY = 
			String.format(SELECT_COLUMNS, ORDER_LINE_ALIAS) +FROM_TABLE +JOIN_PRODUCT_LOCALE +WHERE_CUSTOMER_ORDER_ID;
	private static final String FIND_BY_TICKET_QUERY = 
			String.format(SELECT_COLUMNS, TICKET_ORDER_LINE_ALIAS) +FROM_TABLE +JOIN_PRODUCT_LOCALE +JOIN_TICKET_ORDER_LINE +WHERE_TICKET_ID;
	private static final String FIND_BY_RMA_QUERY = 
			String.format(SELECT_COLUMNS, RMA_ORDER_LINE_ALIAS) +FROM_TABLE +JOIN_PRODUCT_LOCALE +JOIN_RMA_ORDER_LINE +WHERE_RMA_ID;

	private static final String CREATE_QUERY = 
			" INSERT INTO ORDER_LINE(CUSTOMER_ORDER_ID, PRODUCT_ID, QUANTITY, PURCHASE_PRICE, SALE_PRICE)";
	private static final int CREATE_QUERY_COLUMN_COUNT = 5;

	private static final String DELETE_BY_CUSTOMERORDER_QUERY = 
			" DELETE FROM ORDER_LINE ol WHERE ol.CUSTOMER_ORDER_ID = ?";

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

	@Override
	public List<OrderLine> findByCustomerOrder(Connection conn, long orderId, Locale locale) 
			throws DataException {
		return findByKey(conn, FIND_BY_CUSTOMER_ORDER_QUERY, orderId, locale);
	}

	@Override
	public List<OrderLine> findByTicket(Connection conn, long ticketId, Locale locale) throws DataException {
		return findByKey(conn, FIND_BY_TICKET_QUERY, ticketId, locale);
	}

	@Override
	public List<OrderLine> findByRMA(Connection conn, long rmaId, Locale locale) throws DataException {
		return findByKey(conn, FIND_BY_RMA_QUERY, rmaId, locale);
	}

	/**
	 * Execute an SQL SELECT statement from a previously formatted query and a primary key parameter
	 * that is assumed to be of type long.
	 * 
	 * @param conn Connection to use while executing query
	 * @param formattedQuery Complete query containing a single placeholder character for the primary key
	 * @param key Primary key to set to the placeholder character within the statement
	 * @param locale TODO
	 * @return List of order lines returned by the query
	 * @throws DataException if an SQLException is thrown by the driver
	 */
	private List<OrderLine> findByKey(Connection conn, String formattedQuery, long key, Locale locale) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<OrderLine> results = new ArrayList<OrderLine>();	

		try {
			stmt = conn.prepareStatement(formattedQuery);

			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setLong(i++, key);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private OrderLine loadNext(ResultSet rs) throws SQLException {
		OrderLine ol = new OrderLine();
		int i = 1;

		ol.setId(rs.getLong(i++));
		ol.setCustomerOrderId(rs.getLong(i++));
		ol.setProductId(rs.getInt(i++));
		ol.setProductName(rs.getString(i++));
		ol.setQuantity(rs.getShort(i++));
		ol.setPurchasePrice(rs.getDouble(i++));
		ol.setSalePrice(rs.getDouble(i++));
		return ol;
	}

	@Override
	public Boolean create(Connection conn, List<OrderLine> olList) 
			throws DataException {

		if (olList == null || olList.isEmpty()) {
			return null;
		}

		String query = new StringBuilder(CREATE_QUERY)
				.append(SQLQueryUtils.buildPlaceholderValuesClause(olList, CREATE_QUERY_COLUMN_COUNT))
				.toString();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			setInsertValues(stmt, olList);

			int affectedRows = stmt.executeUpdate();

			if (affectedRows != olList.size()) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				for (int i = 0; rs.next(); i++) {
					olList.get(i).setId(rs.getLong(JDBCUtils.GENERATED_KEY_INDEX));
				}
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private int setInsertValues(PreparedStatement stmt, List<OrderLine> olList)
			throws SQLException {

		int index = 1;
		for (OrderLine ol : olList) {
			stmt.setLong(index++, ol.getCustomerOrderId());
			stmt.setLong(index++, ol.getProductId());
			stmt.setShort(index++, ol.getQuantity());
			stmt.setDouble(index++, ol.getPurchasePrice());
			stmt.setDouble(index++, ol.getSalePrice());
		}
		return index;
	}

	@Override
	public Boolean deleteByCustomerOrder(Connection conn, long orderId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_BY_CUSTOMERORDER_QUERY);

			int index = 1;
			stmt.setLong(index++, orderId);

			if (stmt.executeUpdate() < 1) {
				return false;
			} else {
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}	
	}

	@Override
	public Boolean assignToTicket(Connection conn, Ticket t) 
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
	public Boolean assignToRMA(Connection conn, RMA r)
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

}
