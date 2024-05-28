package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;

public interface CustomerOrderService {
	
	public Long create(CustomerOrder po)
			throws ServiceException, DataException;
	
	public Boolean update(CustomerOrder po)
			throws ServiceException, DataException;
	
	public CustomerOrder findById(Long id)
			throws ServiceException, DataException;
	
	public List<CustomerOrder> findByCustomer(Integer customerId)
			throws ServiceException, DataException;
	
	public List<CustomerOrder> findBy(CustomerOrderCriteria criteria)
			throws ServiceException, DataException;
	
	public CustomerOrderRanges getRanges(CustomerOrderCriteria criteria)
			throws ServiceException, DataException;

}
