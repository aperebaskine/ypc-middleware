package com.pinguela.yourpc.dao.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerOrderDAOImpl 
extends AbstractMutableDAO<Long, CustomerOrder>
implements CustomerOrderDAO {

	private static Logger logger = LogManager.getLogger(CustomerOrderDAOImpl.class);

	private static final String SELECT_RANGES =
			" SELECT MIN(co.INVOICE_TOTAL), MAX(co.INVOICE_TOTAL), MIN(co.ORDER_DATE), MAX(co.ORDER_DATE)";
	
	private static final String FROM_TABLE = 
			" FROM CUSTOMER_ORDER co";
	
	private static final String GET_RANGES_QUERY = 
			SELECT_RANGES +FROM_TABLE;
	
	public CustomerOrderDAOImpl() {
	}

	@Override
	public Long create(Session session, CustomerOrder co) 
			throws DataException {
		return super.createEntity(session, co);
	}

	@Override
	public Boolean update(Session session, CustomerOrder co) 
			throws DataException {
		return super.updateEntity(session, co);
	}

	@Override
	public CustomerOrder findById(Session session, Long id) 
			throws DataException {
		return super.findById(session, id);
	}
	
	@Override
	public List<CustomerOrder> findByCustomer(Session session, Integer customerId) 
			throws DataException {
		CustomerOrderCriteria criteria = new CustomerOrderCriteria();
		criteria.setCustomerId(customerId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<CustomerOrder> findBy(Session session, CustomerOrderCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, 
			Root<CustomerOrder> root, AbstractCriteria<CustomerOrder> criteria) {
	    CustomerOrderCriteria coc = (CustomerOrderCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (coc.getCustomerId() != null) {
	        predicates.add(builder.equal(root.get("customer").get("id"), coc.getCustomerId()));
	    }

	    if (coc.getCustomerEmail() != null) {
	        Join<CustomerOrder, Customer> joinCustomer = root.join("customer");
	        joinCustomer.on(builder.equal(joinCustomer.get("email"), coc.getCustomerEmail()));
	    }

	    if (coc.getMinAmount() != null) {
	        predicates.add(builder.ge(root.get("totalPrice"), coc.getMinAmount()));
	    }

	    if (coc.getMaxAmount() != null) {
	        predicates.add(builder.le(root.get("totalPrice"), coc.getMaxAmount()));
	    }

	    if (coc.getMinDate() != null) {
	        predicates.add(builder.greaterThanOrEqualTo(root.get("orderDate"), coc.getMinDate()));
	    }

	    if (coc.getMaxDate() != null) {
	        predicates.add(builder.lessThanOrEqualTo(root.get("orderDate"), coc.getMaxDate()));
	    }

	    if (coc.getState() != null) {
	        predicates.add(builder.equal(root.get("state").get("id"), coc.getState()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<CustomerOrder> query,
			Root<CustomerOrder> root, AbstractCriteria<CustomerOrder> criteria) {
		// Unused	
	}
	
	@Override
	public CustomerOrderRanges getRanges(Session session, CustomerOrderCriteria criteria) 
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

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<CustomerOrder> updateQuery,
			Root<CustomerOrder> root, AbstractUpdateValues<CustomerOrder> updateValues) {
		// Unused
		}

}
