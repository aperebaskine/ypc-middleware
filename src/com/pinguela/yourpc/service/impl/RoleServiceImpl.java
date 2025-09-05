package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.RoleDAO;
import com.pinguela.yourpc.dao.impl.RoleDAOImpl;
import com.pinguela.yourpc.model.Role;
import com.pinguela.yourpc.service.RoleService;
import com.pinguela.yourpc.util.JDBCUtils;

public class RoleServiceImpl 
implements RoleService {
	
	private static Logger logger = LogManager.getLogger(RoleServiceImpl.class);
	
	private RoleDAO roleDAO;
	
	public RoleServiceImpl() {
		roleDAO = new RoleDAOImpl();
	}

	@Override
	public Map<String, Role> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return roleDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
