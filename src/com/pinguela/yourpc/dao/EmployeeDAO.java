package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;

public interface EmployeeDAO {
	
	public Employee findById(Session session, Integer employeeId)
			throws DataException;
	
	public Employee findByUsername(Session session, String username)
			throws DataException;
	
	public List<Employee> findBy(Session session, EmployeeCriteria criteria)
			throws DataException;
	
	public Integer create(Session session, Employee e)
			throws DataException;
	
	public Boolean update(Session session, Employee e)
			throws DataException;
	
	public Boolean updatePassword(Session session, Integer employeeId, String password)
			throws DataException;
	
	public Boolean delete(Session session, Integer employeeId)
			throws DataException;
	
}
