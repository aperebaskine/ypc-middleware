package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.DepartmentDAO;
import com.pinguela.yourpc.dao.impl.DepartmentDAOImpl;
import com.pinguela.yourpc.model.Department;
import com.pinguela.yourpc.service.DepartmentService;
import com.pinguela.yourpc.util.JDBCUtils;

public class DepartmentServiceImpl 
implements DepartmentService {
	
	private static Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);
	
	private DepartmentDAO departmentDAO;
	
	public DepartmentServiceImpl() {
		departmentDAO = new DepartmentDAOImpl();
	}

	@Override
	public Map<String, Department> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return departmentDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
