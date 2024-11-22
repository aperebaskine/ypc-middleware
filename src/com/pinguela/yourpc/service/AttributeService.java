package com.pinguela.yourpc.service;

import java.util.Locale;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.dto.AttributeDTO;

public interface AttributeService {

	public static final boolean RETURN_UNASSIGNED_VALUES = Boolean.TRUE;
	public static final boolean NO_UNASSIGNED_VALUES = Boolean.FALSE;

	public AttributeDTO<?> findById(Integer id, Locale locale, boolean returnUnassigned, Short categoryId)
			throws ServiceException, DataException;

	public AttributeDTO<?> findByName(String name, Locale locale, boolean returnUnassigned, Short categoryId)
			throws ServiceException, DataException;

	public Map<String, AttributeDTO<?>> findByCategory(Short categoryId, Locale locale, boolean returnUnassigned)
			throws ServiceException, DataException;

}
