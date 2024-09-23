package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.RMAState;

public interface RMAStateService {
	
	public Map<String, RMAState> findAll()
			throws ServiceException, DataException;


}
