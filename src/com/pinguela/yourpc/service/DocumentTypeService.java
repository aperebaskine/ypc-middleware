package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.IDType;

public interface DocumentTypeService {
	
	public Map<String, IDType> findAll()
			throws ServiceException, DataException;

}
