package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;

public interface CustomerOrderDAO {
	
	public Long create(Connection conn, CustomerOrder po)
			throws DataException;
	
	public Boolean update(Connection conn, CustomerOrder po)
			throws DataException;
	
	public CustomerOrder findById(Connection conn, Long id)
			throws DataException;
	
	public List<CustomerOrder> findBy(Connection conn, CustomerOrderCriteria criteria)
			throws DataException;

}
