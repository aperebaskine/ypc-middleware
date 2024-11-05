package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.dao.impl.CategoryDAOImpl;
import com.pinguela.yourpc.model.dto.CategoryDTO;
import com.pinguela.yourpc.service.CategoryService;
import com.pinguela.yourpc.util.JDBCUtils;

public class CategoryServiceImpl implements CategoryService {
	
	private static Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
	private CategoryDAO categoryDAO = null;
	
	public CategoryServiceImpl() {
		categoryDAO = new CategoryDAOImpl();
	}

	@Override
	public Map<Short, CategoryDTO> findAll(Locale locale) 
			throws ServiceException, DataException {
		
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return categoryDAO.findAll(conn, locale);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
