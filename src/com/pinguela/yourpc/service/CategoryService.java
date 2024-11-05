package com.pinguela.yourpc.service;

import java.util.Locale;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.dto.CategoryDTO;

public interface CategoryService {
	
	public Map<Short, CategoryDTO> findAll(Locale locale)
			throws ServiceException, DataException;

}
