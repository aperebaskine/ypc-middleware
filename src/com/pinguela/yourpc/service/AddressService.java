package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Address;

public interface AddressService {

	public Address findById(Integer id)
		throws ServiceException, DataException;
	
	public Address findByEmployee(Integer employeeId)
			throws ServiceException, DataException;
	
	public List<Address> findByCustomer(Integer customerId)
			throws ServiceException, DataException;
	
	public Integer create(Address a)
			throws ServiceException, DataException;
	
	public Integer update(Address a)
			throws ServiceException, DataException;
	
	public Boolean delete(Integer id)
			throws ServiceException, DataException;
	
	public Boolean deleteByCustomer(Integer customerId)
			throws ServiceException, DataException;
	
}
