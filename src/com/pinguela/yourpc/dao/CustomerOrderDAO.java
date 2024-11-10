package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;

public interface CustomerOrderDAO {
	
	public Long create(Connection conn, CustomerOrder po)
			throws DataException;
	
	public Boolean update(Connection conn, CustomerOrder po)
			throws DataException;
	
	public CustomerOrder findById(Connection conn, Long id, Locale locale)
			throws DataException;
	
	public List<CustomerOrder> findByCustomer(Connection conn, Integer customerId, Locale locale)
			throws DataException;
	
	public List<CustomerOrder> findBy(Connection conn, CustomerOrderCriteria criteria, Locale locale)
			throws DataException;
	
	public CustomerOrderRanges getRanges(Connection conn, CustomerOrderCriteria criteria)
			throws DataException;

}
