package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.model.RMA;

public interface RMAStateService {
	
	public Map<String, ItemState<RMA>> findAll()
			throws ServiceException, DataException;


}
