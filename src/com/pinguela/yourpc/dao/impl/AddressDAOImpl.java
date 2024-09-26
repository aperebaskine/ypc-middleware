package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;
import org.hibernate.query.criteria.JpaRoot;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.AddressCriteria;
import com.pinguela.yourpc.model.AddressUpdateValues;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.util.comparator.AddressComparator;

import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AddressDAOImpl
extends AbstractMutableDAO<Integer, Address>
implements AddressDAO {
	
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
	
	private boolean updateDefaultAndBilling(Session session, Address a) 
			throws DataException {
		
		if (a.getCustomerId() == null
				|| (!a.isDefault() && !a.isBilling())) {
			return false;
		}			
		
		AddressCriteria criteria = new AddressCriteria();
		criteria.setCustomerId(a.getCustomerId());
		AddressUpdateValues values = 
				new AddressUpdateValues(a.getId(), a.isDefault(), a.isBilling());
		
		return super.updateBy(session, criteria, values);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Address> updateQuery, Root<Address> root,
			AbstractUpdateValues<Address> updateValues) {
		AddressUpdateValues values = (AddressUpdateValues) updateValues;
		
		if (values.isDefault()) {
			Path<Expression<Boolean>> isDefault = root.get("isDefault");
			Expression<Boolean> idEqualsExpression = builder.equal(root.get("id"), values.getAddressId());
			updateQuery.set(isDefault, idEqualsExpression);
		}
		if (values.isBilling()) {
			Path<Expression<Boolean>> isBilling = root.get("isBilling");
			Expression<Boolean> idEqualsExpression = builder.equal(root.get("id"), values.getAddressId());
			updateQuery.set(isBilling, idEqualsExpression);
		}
	}

	private Boolean isOrderAddress(Session session, Integer addressId) 
			throws DataException {
		
		try {
			HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
			JpaCriteriaQuery<Tuple> query = builder.createTupleQuery();
			JpaRoot<CustomerOrder> root = query.from(CustomerOrder.class);
			
			Predicate condition = builder.or(
					builder.equal(root.get("billingAddress").get("id"), addressId),
					builder.equal(root.get("shippingAddress").get("id"), addressId));
			
			query.where(condition);
			
			return session
					.createQuery(query.createCountQuery())
					.getSingleResult() > 0;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public void setDefault(Session session, Integer addressId) 
			throws DataException {
		Address a = session.find(getTargetClass(), addressId);
		a.setDefault(true);
		updateDefaultAndBilling(session, a);
	}

	@Override
	public void setBilling(Session session, Integer addressId) 
			throws DataException {
		Address a = session.find(getTargetClass(), addressId);
		a.setBilling(true);
		updateDefaultAndBilling(session, a);
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

}
