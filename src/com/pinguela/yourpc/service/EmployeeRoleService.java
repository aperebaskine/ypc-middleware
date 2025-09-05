package com.pinguela.yourpc.service;

import com.pinguela.DataException;
import com.pinguela.ServiceException;

public interface EmployeeRoleService {
	
	public Integer assignToEmployee(Integer employeeId, String roleId)
			throws ServiceException, DataException;
	
	public Boolean unassign(Integer employeeId)
			throws ServiceException, DataException;

}
