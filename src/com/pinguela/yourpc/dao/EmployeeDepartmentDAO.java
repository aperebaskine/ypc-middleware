package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EmployeeDepartment;

public interface EmployeeDepartmentDAO {
	
	public List<EmployeeDepartment> findByEmployee(Connection conn, Integer employeeId)
			throws DataException;

}
