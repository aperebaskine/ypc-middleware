package com.pinguela.yourpc.dao;

import java.util.Locale;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.dto.CategoryDTO;

public interface CategoryDAO {
	
	public Map<Short, CategoryDTO> findAll(Session session, Locale locale)
			throws DataException;

}
