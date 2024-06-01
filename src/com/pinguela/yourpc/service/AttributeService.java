package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Attribute;

public interface AttributeService {
	
	public static final boolean RETURN_UNASSIGNED_VALUES = Boolean.TRUE;
	public static final boolean NO_UNASSIGNED_VALUES = Boolean.FALSE;
	
	public Attribute<?> findById(Long id, boolean returnUnassigned)
			throws ServiceException, DataException;
	
	public Map<String, Attribute<?>> findByCategory(Short categoryId, boolean returnUnassigned)
			throws ServiceException, DataException;

}
