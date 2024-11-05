package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Locale;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.dto.CategoryDTO;

public interface CategoryDAO {
	
	public Map<Short, CategoryDTO> findAll(Connection conn, Locale locale)
			throws DataException;

}
