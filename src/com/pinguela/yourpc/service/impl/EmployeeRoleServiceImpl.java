package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.EmployeeRoleDAO;
import com.pinguela.yourpc.dao.impl.EmployeeRoleDAOImpl;
import com.pinguela.yourpc.service.EmployeeRoleService;
import com.pinguela.yourpc.util.JDBCUtils;

public class EmployeeRoleServiceImpl implements EmployeeRoleService {
	
	private static Logger logger = LogManager.getLogger(EmployeeRoleServiceImpl.class);
	
	private EmployeeRoleDAO employeeRoleDAO = null;
	
	public EmployeeRoleServiceImpl() {
		employeeRoleDAO = new EmployeeRoleDAOImpl();
	}

	@Override
	public Integer assignToEmployee(Integer employeeId, String roleId) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			return employeeRoleDAO.assignToEmployee(conn, employeeId, roleId);
			
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
			return employeeRoleDAO.unassign(conn, employeeId);
			
		} catch (SQLException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

}
