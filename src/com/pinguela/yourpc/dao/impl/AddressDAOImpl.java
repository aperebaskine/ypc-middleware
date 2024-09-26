package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.AddressCriteria;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.comparator.AddressComparator;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AddressDAOImpl
extends AbstractMutableDAO<Integer, Address>
implements AddressDAO {

	private static final String IS_DEFAULT_COLUMN = "IS_DEFAULT";
	private static final String IS_BILLING_COLUMN = "IS_BILLING";

	private static final String IS_ORDER_ADDRESS_QUERY =
			" SELECT co.ID"
					+ " FROM CUSTOMER_ORDER co"
					+ " WHERE BILLING_ADDRESS_ID = ?"
					+ " OR SHIPPING_ADDRESS_ID = ?";

	private static final String SET_DEFAULT_PLACEHOLDER_QUERY =
			" UPDATE ADDRESS a"
					+ "	INNER JOIN ADDRESS b"
					+ "	ON a.CUSTOMER_ID = b.CUSTOMER_ID"
					+ " SET a.%1$s = (a.ID = ?)"
					+ " WHERE b.ID = ?";
	
	private static final AddressComparator COMPARATOR = new AddressComparator();
	
	private static Logger logger = LogManager.getLogger(AddressDAOImpl.class);

	public AddressDAOImpl() {
	}

	@Override
	public Address findById(Session session, Integer id)
			throws DataException {
			return super.findById(session, id);
	}

	@Override
	public Address findByEmployee(Session session, Integer employeeId)
			throws DataException {
		AddressCriteria criteria = new AddressCriteria();
		criteria.setEmployeeId(employeeId);
		return super.findSingleResultBy(session, criteria);
	}

	@Override
	public List<Address> findByCustomer(Session session, Integer customerId)
			throws DataException {
		AddressCriteria criteria = new AddressCriteria();
		criteria.setEmployeeId(customerId);
		return super.findBy(session, criteria);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Address> root, 
			AbstractCriteria<Address> criteria) {
	    AddressCriteria addressCriteria = (AddressCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();
	    
	    if (addressCriteria.getCustomerId() != null) {
	        predicates.add(builder.equal(
	        		root.get("customer").get("id"), addressCriteria.getCustomerId()));
	    }
	    
	    if (addressCriteria.getEmployeeId() != null) {
	        predicates.add(builder.equal(
	        		root.get("employee").get("id"), addressCriteria.getEmployeeId()));
	    }
	    
	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Address> query, Root<Address> root,
			AbstractCriteria<Address> criteria) {
		// Unused	
	}

	@Override
	public Integer create(Session session, Address a)
			throws DataException {
		return super.createEntity(session, a);
	}

	@Override
	public Integer update(Session session, Address a)
			throws DataException {
		
		if (isOrderAddress(session, a.getId())) { // Create new entry in database if necessary
			Address persistentAddress = findById(session, a.getId());
			if (COMPARATOR.compare(a, persistentAddress) != 0) {
				delete(session, a.getId());
				return create(session, a);
			}
		}

		super.updateEntity(session, a);
		return a.getId();
	}
	
	private void updateDefaultAndBilling(Connection conn, Address a) throws DataException {
		if (a.getCustomerId() == null) {
			return;
		}			
		if (a.isDefault()) {
			setDefault(conn, a.getId());
		}
		if (a.isBilling()) {
			setBilling(conn, a.getId());
		}
	}

	private Boolean isOrderAddress(Session session, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(IS_ORDER_ADDRESS_QUERY);
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);

			rs = stmt.executeQuery();
			return rs.next();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}	
	}

	@Override
	public void setDefault(Session session, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(String.format(SET_DEFAULT_PLACEHOLDER_QUERY, IS_DEFAULT_COLUMN));
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public void setBilling(Session session, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(String.format(SET_DEFAULT_PLACEHOLDER_QUERY, IS_BILLING_COLUMN));
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public Boolean delete(Session session, Integer id)
			throws DataException {
		return super.deleteEntity(session, id);
	}

	public Boolean deleteByCustomer(Session session, Integer customerId)
			throws DataException {
		AddressCriteria criteria = new AddressCriteria();
		criteria.setCustomerId(customerId);
		return super.deleteBy(session, criteria);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Address> updateQuery, Root<Address> root,
			AbstractUpdateValues<Address> updateValues) {
		// Unused	
	}

}
