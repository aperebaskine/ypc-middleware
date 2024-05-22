package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.ItemState;

public interface OrderStateDAO {
	
	public Map<String, ItemState<CustomerOrder>> findAll(Connection conn)
			throws DataException;

}
