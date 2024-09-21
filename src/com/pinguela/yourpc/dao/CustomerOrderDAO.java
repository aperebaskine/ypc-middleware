package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;

public interface CustomerOrderDAO {
	
	public Long create(Session session, CustomerOrder po)
			throws DataException;
	
	public Boolean update(Session session, CustomerOrder po)
			throws DataException;
	
	public CustomerOrder findById(Session session, Long id)
			throws DataException;
	
	public List<CustomerOrder> findByCustomer(Session session, Integer customerId)
			throws DataException;
	
	public List<CustomerOrder> findBy(Session session, CustomerOrderCriteria criteria)
			throws DataException;
	
	public CustomerOrderRanges getRanges(Session session, CustomerOrderCriteria criteria)
			throws DataException;

}
