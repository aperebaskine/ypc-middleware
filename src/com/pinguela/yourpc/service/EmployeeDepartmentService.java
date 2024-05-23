package com.pinguela.yourpc.service;

import com.pinguela.DataException;
import com.pinguela.ServiceException;

public interface EmployeeDepartmentService {
	
	public Integer assignToEmployee(Integer employeeId, String departmentId)
			throws ServiceException, DataException;
	
	public Boolean unassign(Integer employeeId)
			throws ServiceException, DataException;

}
