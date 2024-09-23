package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;
import com.pinguela.yourpc.model.OrderState;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class CustomerOrderDAOImpl 
extends AbstractDAO<CustomerOrder>
implements CustomerOrderDAO {

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
		super(CustomerOrder.class);
		orderLineDAO = new OrderLineDAOImpl();
	}

	@Override
	public Long create(Session session, CustomerOrder co) 
			throws DataException {
		return (Long) super.persist(session, co);
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
				orderLineDAO.persist(conn, co.getOrderLines());
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
	public CustomerOrder findById(Session session, Long id) 
			throws DataException {
		return super.findById(session, id);
	}
	
	@Override
	public List<CustomerOrder> findByCustomer(Session session, Integer customerId) 
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<CustomerOrder> query = builder.createQuery(getTargetClass());
			Root<CustomerOrder> root = query.from(getTargetClass());
			
			Join<CustomerOrder, Customer> customerJoin = root.join("customer");
			query.where(builder.equal(customerJoin.get("id"), customerId));
			
			return session.createQuery(query).getResultList();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public List<CustomerOrder> findBy(Session session, CustomerOrderCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<CustomerOrder> query,
			Root<CustomerOrder> root, AbstractCriteria<CustomerOrder> criteria) {
		CustomerOrderCriteria coc = (CustomerOrderCriteria) criteria;
		Join<CustomerOrder, Customer> joinCustomer = null;
		
		if (coc.getCustomerId() != null) {
			joinCustomer = root.join("customer");
			query.where(builder.equal(joinCustomer.get("id"), coc.getCustomerId()));
		}
		if (coc.getCustomerEmail() != null) {
			if (joinCustomer == null) {
				joinCustomer = root.join("customer");
			}
			query.where(builder.equal(joinCustomer.get("email"), coc.getCustomerEmail()));
		}
		if (coc.getMinAmount() != null) {
			query.where(builder.ge(root.get("totalPrice"), coc.getMinAmount()));
		}
		if (coc.getMaxAmount() != null) {
			query.where(builder.le(root.get("totalPrice"), coc.getMaxAmount()));
		}
		if (coc.getMinDate() != null) {
			query.where(builder.greaterThanOrEqualTo(root.get("orderDate"), coc.getMinDate()));
		}
		if (coc.getMaxDate() != null) {
			query.where(builder.lessThanOrEqualTo(root.get("orderDate"), coc.getMaxDate()));
		}
		if (coc.getState() != null) {
			Join<CustomerOrder, OrderState> joinState = root.join("state");
			query.where(builder.equal(joinState.get("id"), coc.getState()));
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

	private StringBuilder buildWhereClause(CustomerOrderCriteria coc) {
		List<String> filledCriteria = new ArrayList<String>();
		
		if (coc.getCustomerId() != null) {
			filledCriteria.add(" co.CUSTOMER_ID = ?");
		}
		if (coc.getCustomerEmail() != null) {
			filledCriteria.add(" c.EMAIL = ?");
		}
		if (coc.getMinAmount() != null) {
			filledCriteria.add(" co.INVOICE_TOTAL >= ?");
		}
		if (coc.getMaxAmount() != null) {
			filledCriteria.add(" co.INVOICE_TOTAL <= ?");
		}
		if (coc.getMinDate() != null) {
			filledCriteria.add(" co.ORDER_DATE >= ?");
		}
		if (coc.getMaxDate() != null) {
			filledCriteria.add(" co.ORDER_DATE <= ?");
		}
		if (coc.getState() != null) {
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
