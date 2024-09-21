package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Category;

public interface CategoryDAO {
	
	public Map<Short, Category> findAll(Session session)
			throws DataException;

}
