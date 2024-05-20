package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;

public interface EmployeeService {
	
	public Employee login(String email, String password)
			throws ServiceException, DataException;

	public Integer register(Employee e)
			throws ServiceException, DataException;

	public Employee findById(Integer employeeId)
			throws ServiceException, DataException;
	
	public Employee findByUsername(String username)
			throws ServiceException, DataException;

	public List<Employee> findBy(EmployeeCriteria criteria)
			throws ServiceException, DataException;
	
	public Boolean update(Employee e)
			throws ServiceException, DataException;
	
	public Boolean updatePassword(Integer employeeId, String password)
			throws ServiceException, DataException;

	public Boolean delete(Integer employeeId)
			throws ServiceException, DataException;

}
