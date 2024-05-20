package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Category;

public interface CategoryDAO {
	
	public Map<Short, Category> findAll(Connection conn)
			throws DataException;

}
