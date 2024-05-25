package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.DocumentType;

public interface DocumentTypeService {
	
	public Map<String, DocumentType> findAll()
			throws ServiceException, DataException;

}
