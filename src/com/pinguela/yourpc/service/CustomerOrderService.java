package com.pinguela.yourpc.service;

import java.util.List;
import java.util.Locale;

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
	
	public CustomerOrder findById(Long id, Locale locale)
			throws ServiceException, DataException;
	
	public List<CustomerOrder> findByCustomer(Integer customerId, Locale locale)
			throws ServiceException, DataException;
	
	public List<CustomerOrder> findBy(CustomerOrderCriteria criteria, Locale locale)
			throws ServiceException, DataException;
	
	public CustomerOrderRanges getRanges(CustomerOrderCriteria criteria)
			throws ServiceException, DataException;

}
