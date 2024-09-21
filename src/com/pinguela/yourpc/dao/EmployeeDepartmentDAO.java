package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EmployeeDepartment;

public interface EmployeeDepartmentDAO {
	
	public List<EmployeeDepartment> findByEmployee(Session session, Integer employeeId)
			throws DataException;
	
	public Integer assignToEmployee(Session session, Integer employeeId, String departmentId)
			throws DataException;
	
	public Boolean unassign(Session session, Integer employeeId)
			throws DataException;

}
