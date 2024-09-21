package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.EntityState;

public interface OrderStateDAO {
	
	public Map<String, EntityState<CustomerOrder>> findAll(Session session)
			throws DataException;

}
