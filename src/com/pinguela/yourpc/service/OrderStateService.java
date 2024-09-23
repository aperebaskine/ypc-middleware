package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.OrderState;

public interface OrderStateService {
	
	public Map<String, OrderState> findAll()
			throws ServiceException, DataException;

}
