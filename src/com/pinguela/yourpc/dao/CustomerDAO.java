package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;

public interface CustomerDAO {
	
	public Customer findById(Connection conn, Integer customerId)
			throws DataException;
	
	public Customer findByEmail(Connection conn, String email)
			throws DataException;
	
	public List<Customer> findBy(Connection conn, CustomerCriteria criteria)
			throws DataException;
	
	public boolean emailExists(Connection conn, String email)
			throws DataException;
	
	public boolean phoneNumberExists(Connection conn, String phoneNumber)
			throws DataException;
	
	public Integer create(Connection conn, Customer c)
			throws DataException;
	
	public Boolean update(Connection conn, Customer c)
			throws DataException;
	
	public Boolean updatePassword(Connection conn, Integer customerId, String password)
			throws DataException;
	
	public Boolean delete(Connection conn, Integer customerId)
			throws DataException;
	
}
