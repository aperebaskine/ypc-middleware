package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.ItemState;

public interface OrderStateService {
	
	public Map<String, ItemState<CustomerOrder>> findAll()
			throws ServiceException, DataException;

}
