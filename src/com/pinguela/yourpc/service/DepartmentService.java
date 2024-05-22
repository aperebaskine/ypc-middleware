package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Department;

public interface DepartmentService {
	
	public Map<String, Department> findAll()
			throws ServiceException, DataException;

}
