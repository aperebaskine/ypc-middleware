package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.impl.AttributeDAOImpl;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.util.JDBCUtils;

public class AttributeServiceImpl implements AttributeService {
	
	private static Logger logger = LogManager.getLogger(AttributeServiceImpl.class);
	private AttributeDAO attributeDAO = null;
	
	public AttributeServiceImpl() {
		attributeDAO = new AttributeDAOImpl();
	}
	
	@Override
	public Attribute<?> findById(Long id, boolean returnUnassigned) 
			throws ServiceException, DataException {
		
		if (id == null) {
			return null;
		}
		
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return attributeDAO.findById(conn, id, returnUnassigned);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Map<String, Attribute<?>> findByCategory(Short categoryId, boolean returnUnassigned)
			throws ServiceException, DataException {
		
		if (categoryId == null) {
			return null;
		}
		
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return attributeDAO.findByCategory(conn, categoryId, returnUnassigned);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}		
	}

}
