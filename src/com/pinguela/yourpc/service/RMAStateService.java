package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.RMA;

public interface RMAStateService {
	
	public Map<String, EntityState<RMA>> findAll()
			throws ServiceException, DataException;


}
