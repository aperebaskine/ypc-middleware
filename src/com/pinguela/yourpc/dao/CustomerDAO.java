package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;

public interface CustomerDAO {
	
	public Customer findById(Session session, Integer customerId)
			throws DataException;
	
	public Customer findByEmail(Session session, String email)
			throws DataException;
	
	public List<Customer> findBy(Session session, CustomerCriteria criteria)
			throws DataException;
	
	public Integer create(Session session, Customer c)
			throws DataException;
	
	public Boolean update(Session session, Customer c)
			throws DataException;
	
	public Boolean updatePassword(Session session, Integer customerId, String password)
			throws DataException;
	
	public Boolean delete(Session session, Integer customerId)
			throws DataException;
	
}
