package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.OrderState;

public interface OrderStateDAO {
	
	public Map<String, OrderState> findAll(Session session)
			throws DataException;

}
