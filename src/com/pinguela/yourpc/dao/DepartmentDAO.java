package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Department;

public interface DepartmentDAO {
	
	public Map<String, Department> findAll(Connection conn)
			throws DataException;

}
