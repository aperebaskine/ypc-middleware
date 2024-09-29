package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.model.Customer_;
import com.pinguela.yourpc.model.FullName_;
import com.pinguela.yourpc.model.ID_;
import com.pinguela.yourpc.model.SimpleUpdateValue;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerDAOImpl 
extends AbstractMutableDAO<Integer, Customer>
implements CustomerDAO {

	public CustomerDAOImpl() {
	}

	@Override
	public Customer findById(Session session, Integer customerId) 
			throws DataException {
		return super.findById(session, customerId);
	}

	@Override
	public Customer findByEmail(Session session, String email) throws DataException {
		CustomerCriteria criteria = new CustomerCriteria();
		criteria.setEmail(email);
		return super.findSingleResultBy(session, criteria);
	}

	@Override
	public List<Customer> findBy(Session session, CustomerCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Customer> root, AbstractCriteria<Customer> criteria) {
	    CustomerCriteria customerCriteria = (CustomerCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (customerCriteria.getId() != null) {
	        predicates.add(builder.equal(root.get(Customer_.id), customerCriteria.getId()));
	    }
	    if (customerCriteria.getEmail() != null) {
	        predicates.add(builder.equal(root.get(Customer_.email), customerCriteria.getEmail()));
	    }
	    if (customerCriteria.getFirstName() != null) {
	        predicates.add(builder.like(root.get(Customer_.name).get(FullName_.firstName), 
	                SQLQueryUtils.wrapLike(customerCriteria.getFirstName())));
	    }
	    if (customerCriteria.getLastName1() != null) {
	        predicates.add(builder.like(root.get(Customer_.name).get(FullName_.lastName1), 
	                SQLQueryUtils.wrapLike(customerCriteria.getLastName1())));
	    }
	    if (customerCriteria.getLastName2() != null) {
	        predicates.add(builder.like(root.get(Customer_.name).get(FullName_.lastName2), 
	                SQLQueryUtils.wrapLike(customerCriteria.getLastName2())));
	    }
	    if (customerCriteria.getDocumentNumber() != null) {
	        predicates.add(builder.equal(root.get(Customer_.document).get(ID_.number), 
	                customerCriteria.getDocumentNumber()));
	    }
	    if (customerCriteria.getPhoneNumber() != null) {
	        predicates.add(builder.equal(root.get(Customer_.phoneNumber), customerCriteria.getPhoneNumber()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Customer> query, Root<Customer> root,
			AbstractCriteria<Customer> criteria) {
		// Unused	
	}

	@Override
	public Integer create(Session session, Customer c) 
			throws DataException {
		return create(session, c);
	}

	@Override
	public Boolean update(Session session, Customer c) 
			throws DataException {
		return super.updateEntity(session, c);
	} 

	@Override
	public Boolean updatePassword(Session session, Integer customerId, String password) throws DataException {
		CustomerCriteria criteria = new CustomerCriteria();
		criteria.setId(customerId);
		SimpleUpdateValue<Customer> values = new SimpleUpdateValue<>(password);
		
		return super.updateBy(session, criteria, values);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Customer> updateQuery, Root<Customer> root,
			AbstractUpdateValues<Customer> updateValues) {
		updateQuery.set(root.get(Customer_.encryptedPassword), (String) ((SimpleUpdateValue<Customer>) updateValues).getValue());
	}

	@Override
	public Boolean delete(Session session, Integer customerId) 
			throws DataException {
		return super.deleteEntity(session, customerId);
	}

}
