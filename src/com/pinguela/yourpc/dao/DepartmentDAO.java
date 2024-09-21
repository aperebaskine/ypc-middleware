package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Department;

public interface DepartmentDAO {
	
	public Map<String, Department> findAll(Session session)
			throws DataException;

}
