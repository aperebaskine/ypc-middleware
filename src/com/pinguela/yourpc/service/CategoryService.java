package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Category;

public interface CategoryService {
	
	public Map<Short, Category> findAll()
			throws ServiceException, DataException;

}
