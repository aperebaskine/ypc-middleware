package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Address;

public interface AddressDAO {
	
	public Address findById(Connection conn, Integer id)
		throws DataException;
	
	public Address findByEmployee(Connection conn, Integer employeeId)
			throws DataException;
	
	public List<Address> findByCustomer(Connection conn, Integer customerId)
			throws DataException;
	
	public boolean matchesCustomer(Connection conn, Integer addressId, Integer customerId)
			throws DataException;
	
	public Integer create(Connection conn, Address a)
			throws DataException;
	
	public Integer update(Connection conn, Address a)
			throws DataException;
	
	public void setDefault(Connection conn, Integer addressId)
			throws DataException;
	
	public void setBilling(Connection conn, Integer addressId)
			throws DataException;
	
	public Boolean delete(Connection conn, Integer id)
			throws DataException;
	
	public Boolean deleteByCustomer(Connection conn, Integer customerId)
			throws DataException;
	
}
