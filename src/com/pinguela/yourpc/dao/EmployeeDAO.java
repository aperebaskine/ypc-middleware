package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;

public interface EmployeeDAO {
	
	public Employee findById(Connection conn, Integer employeeId)
			throws DataException;
	
	public Employee findByUsername(Connection conn, String username)
			throws DataException;
	
	public List<Employee> findBy(Connection conn, EmployeeCriteria criteria)
			throws DataException;
	
	public Integer create(Connection conn, Employee e)
			throws DataException;
	
	public Boolean update(Connection conn, Employee e)
			throws DataException;
	
	public Boolean updatePassword(Connection conn, Integer employeeId, String password)
			throws DataException;
	
	public Boolean delete(Connection conn, Integer employeeId)
			throws DataException;
	
}
