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
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class CustomerOrderDAOImpl implements CustomerOrderDAO {

	private static Logger logger = LogManager.getLogger(CustomerOrderDAOImpl.class);
	private OrderLineDAO orderLineDAO = null;
	
	private static final String SELECT_COLUMNS =
			" SELECT co.ID, co.ORDER_STATE_ID, co.CUSTOMER_ID, co.ORDER_DATE, co.TRACKING_NUMBER, co.BILLING_ADDRESS_ID, co.SHIPPING_ADDRESS_ID, co.INVOICE_TOTAL";

	private static final String SELECT_RANGES =
			" SELECT MIN(co.INVOICE_TOTAL), MAX(co.INVOICE_TOTAL), MIN(co.ORDER_DATE), MAX(co.ORDER_DATE)";
	
	private static final String FROM_TABLE = 
			" FROM CUSTOMER_ORDER co";
	
	private static final String GET_RANGES_QUERY = 
			SELECT_RANGES +FROM_TABLE;

	private static final String CREATE_QUERY = 
			" INSERT INTO CUSTOMER_ORDER(ORDER_STATE_ID,"
					+ " CUSTOMER_ID,"
					+ " ORDER_DATE,"
					+ " TRACKING_NUMBER,"
					+ " BILLING_ADDRESS_ID,"
					+ " SHIPPING_ADDRESS_ID,"
					+ " INVOICE_TOTAL)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY =
			" UPDATE CUSTOMER_ORDER"
					+ " SET ORDER_STATE_ID = ?,"
					+ " CUSTOMER_ID = ?,"
					+ " ORDER_DATE = ?,"
					+ " TRACKING_NUMBER = ?,"
					+ " BILLING_ADDRESS_ID = ?,"
					+ " SHIPPING_ADDRESS_ID = ?,"
					+ " INVOICE_TOTAL = ?"
					+ " WHERE ID = ?";
	
	public CustomerOrderDAOImpl() {
		orderLineDAO = new OrderLineDAOImpl();
	}

	@Override
	public Long create(Connection conn, CustomerOrder co) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			co.setOrderDate(new Date());
			setInsertValues(stmt, co);

			long insertedRows = stmt.executeUpdate();
			
			if (insertedRows != 1) {
				
				logger.error(LogMessages.INSERT_FAILED, co);
				throw new DataException(ErrorCodes.INSERT_FAILED);
				
			} else {
				
				rs = stmt.getGeneratedKeys();
				rs.next();
				co.setId(rs.getLong(JDBCUtils.GENERATED_KEY_INDEX));
				
				for (OrderLine ol : co.getOrderLines()) {
					
					ol.setCustomerOrderId(co.getId());
				}
				
				logger.info(new StringBuilder("Nuevo pedido con ID ")
							.append(co.getId())
							.append(" insertado."));
				orderLineDAO.create(conn, co.getOrderLines());
				return co.getId();
			}
			
		} catch (SQLException sqle) {
			
			logger.error(sqle);
			throw new DataException(sqle);
			
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public Boolean update(Connection conn, CustomerOrder co) 
			throws DataException {

		PreparedStatement stmt = null;

		try {

			orderLineDAO.deleteByCustomerOrder(conn, co.getId());
			stmt = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);

			int index = 1;
			index = setInsertValues(stmt, co);
			stmt.setLong(index, co.getId());

			int updatedRows = stmt.executeUpdate();
			if (updatedRows != 1) {
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			} else {
				orderLineDAO.create(conn, co.getOrderLines());
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private int setInsertValues(PreparedStatement stmt, CustomerOrder co) throws SQLException {

		int index = 1;
		stmt.setString(index++, co.getState());
		stmt.setLong(index++, co.getCustomerId());
		stmt.setTimestamp(index++, new java.sql.Timestamp(co.getOrderDate().getTime()));
		stmt.setString(index++, co.getTrackingNumber());
		stmt.setInt(index++, co.getBillingAddressId());
		stmt.setInt(index++, co.getShippingAddressId());
		stmt.setDouble(index++, co.getTotalPrice());

		return index;
	}

	@Override
	public CustomerOrder findById(Connection conn, Long id) 
			throws DataException {
		
		if (id == null) {
			return null;
		}
		

		String query = SELECT_COLUMNS
				+ FROM_TABLE
				+ " WHERE ID = ?";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		CustomerOrder co = null;

		try {

			stmt = conn.prepareStatement(query);

			int index = 1;
			stmt.setLong(index++, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				co = loadNext(conn, rs);
			}
			return co;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	@Override
	public List<CustomerOrder> findByCustomer(Connection conn, Integer customerId) 
			throws DataException {
		
		if (customerId == null) {
			return null;
		}
		

		String query = SELECT_COLUMNS
				+ FROM_TABLE
				+ " WHERE CUSTOMER_ID = ?"
				+ " ORDER BY ORDER_DATE DESC";

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CustomerOrder> results = new ArrayList<>();

		try {

			stmt = conn.prepareStatement(query);

			int index = 1;
			stmt.setLong(index++, customerId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(conn, rs));
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public List<CustomerOrder> findBy(Connection conn, CustomerOrderCriteria criteria) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<CustomerOrder> results = new ArrayList<CustomerOrder>();

		try {

			stmt = conn.prepareStatement(buildFindByQuery(criteria));
			setQueryValues(stmt, criteria);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(conn, rs));
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	@Override
	public CustomerOrderRanges getRanges(Connection conn, CustomerOrderCriteria criteria) 
			throws DataException {
		
		CustomerOrderRanges ranges = new CustomerOrderRanges();

		StringBuilder query = new StringBuilder(GET_RANGES_QUERY).append(buildWhereClause(criteria));
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			setQueryValues(stmt, criteria);

			rs = stmt.executeQuery();
			rs.next();
			
			int i = 1;
			ranges.setMinAmount(rs.getDouble(i++));
			ranges.setMaxAmount(rs.getDouble(i++));
			ranges.setMinDate(rs.getDate(i++));
			ranges.setMaxDate(rs.getDate(i++));
			
			return ranges;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private String buildFindByQuery(CustomerOrderCriteria criteria) {

		StringBuilder query = new StringBuilder(SELECT_COLUMNS);
		

		// SELECT FROM
		if (criteria.getCustomerEmail() != null) {
			query.append(", c.EMAIL");
		}
		query.append(FROM_TABLE);
		if (criteria.getCustomerEmail() != null) {
			query.append(" INNER JOIN CUSTOMER c "
					+ "ON c.ID = co.CUSTOMER_ID");				
		}

		return query
				.append(buildWhereClause(criteria))
				.append(SQLQueryUtils.buildOrderByClause(criteria.getOrderBy(), CustomerOrder.class))
				.toString();
	}

	private StringBuilder buildWhereClause(CustomerOrderCriteria criteria) {
		List<String> filledCriteria = new ArrayList<String>();
		
		if (criteria.getCustomerId() != null) {
			filledCriteria.add(" co.CUSTOMER_ID = ?");
		}
		if (criteria.getCustomerEmail() != null) {
			filledCriteria.add(" c.EMAIL = ?");
		}
		if (criteria.getMinAmount() != null) {
			filledCriteria.add(" co.INVOICE_TOTAL >= ?");
		}
		if (criteria.getMaxAmount() != null) {
			filledCriteria.add(" co.INVOICE_TOTAL <= ?");
		}
		if (criteria.getMinDate() != null) {
			filledCriteria.add(" co.ORDER_DATE >= ?");
		}
		if (criteria.getMaxDate() != null) {
			filledCriteria.add(" co.ORDER_DATE <= ?");
		}
		if (criteria.getState() != null) {
			filledCriteria.add(" co.ORDER_STATE_ID = ?");
		}
		return SQLQueryUtils.buildWhereClause(filledCriteria);
	}

	private void setQueryValues(PreparedStatement stmt, CustomerOrderCriteria criteria) throws SQLException {

		int index = 1;

		if (criteria.getCustomerId() != null) {
			stmt.setLong(index++, criteria.getCustomerId());
		}
		if (criteria.getCustomerEmail() != null) {
			stmt.setString(index++, criteria.getCustomerEmail().toLowerCase());
		}
		if (criteria.getMinAmount() != null) {
			stmt.setDouble(index++, criteria.getMinAmount());
		}
		if (criteria.getMaxAmount() != null) {
			stmt.setDouble(index++, criteria.getMaxAmount());
		}
		if (criteria.getMinDate() != null) {
			stmt.setDate(index++, new java.sql.Date(criteria.getMinDate().getTime()));
		}
		if (criteria.getMaxDate() != null) {
			stmt.setDate(index++, new java.sql.Date(criteria.getMaxDate().getTime()));
		}
		if (criteria.getState() != null) {
			stmt.setString(index++, criteria.getState().toString());
		}
	}

	private CustomerOrder loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		CustomerOrder co = new CustomerOrder();
		int i = 1;

		co.setId(rs.getLong(i++));
		co.setState(rs.getString(i++));
		co.setCustomerId(rs.getInt(i++));
		co.setOrderDate(rs.getTimestamp(i++));
		co.setTrackingNumber(rs.getString(i++));
		co.setBillingAddressId(rs.getInt(i++));
		co.setShippingAddressId(rs.getInt(i++));
		co.setTotalPrice(rs.getDouble(i++));
		co.setOrderLines(orderLineDAO.findByCustomerOrder(conn, co.getId()));
		return co;
	}

}
