package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
import com.pinguela.yourpc.dao.impl.EmployeeDepartmentDAOImpl;
import com.pinguela.yourpc.service.EmployeeDepartmentService;
import com.pinguela.yourpc.util.JDBCUtils;

public class EmployeeDepartmentServiceImpl implements EmployeeDepartmentService {
	
	private static Logger logger = LogManager.getLogger(EmployeeDepartmentServiceImpl.class);
	
	private EmployeeDepartmentDAO employeeDepartmentDAO = null;
	
	public EmployeeDepartmentServiceImpl() {
		employeeDepartmentDAO = new EmployeeDepartmentDAOImpl();
	}

	@Override
	public Integer assignToEmployee(Integer employeeId, String departmentId) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			return employeeDepartmentDAO.assignToEmployee(conn, employeeId, departmentId);
			
		} catch (SQLException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean unassign(Integer employeeId) throws ServiceException, DataException {
		
		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			return employeeDepartmentDAO.unassign(conn, employeeId);
			
		} catch (SQLException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

}
