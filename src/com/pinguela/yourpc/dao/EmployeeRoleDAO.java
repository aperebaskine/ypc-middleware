package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EmployeeRole;

public interface EmployeeRoleDAO {
	
	public List<EmployeeRole> findByEmployee(Connection conn, Integer employeeId)
			throws DataException;
	
	public Integer assignToEmployee(Connection conn, Integer employeeId, String roleId)
			throws DataException;
	
	public Boolean unassign(Connection conn, Integer employeeId)
			throws DataException;

}
