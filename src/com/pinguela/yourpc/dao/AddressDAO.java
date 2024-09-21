package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Address;

public interface AddressDAO {
	
	public Address findById(Session session, Integer id)
		throws DataException;
	
	public Address findByEmployee(Session session, Integer employeeId)
			throws DataException;
	
	public List<Address> findByCustomer(Session session, Integer customerId)
			throws DataException;
	
	public Integer create(Session session, Address a)
			throws DataException;
	
	public Integer update(Session session, Address a)
			throws DataException;
	
	public void setDefault(Session session, Integer addressId)
			throws DataException;
	
	public void setBilling(Session session, Integer addressId)
			throws DataException;
	
	public Boolean delete(Session session, Integer id)
			throws DataException;
	
	public Boolean deleteByCustomer(Session session, Integer customerId)
			throws DataException;
	
}
