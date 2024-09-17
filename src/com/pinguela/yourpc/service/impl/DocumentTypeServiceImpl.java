package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.DocumentTypeDAO;
import com.pinguela.yourpc.dao.impl.DocumentTypeDAOImpl;
import com.pinguela.yourpc.model.IDType;
import com.pinguela.yourpc.service.DocumentTypeService;
import com.pinguela.yourpc.util.JDBCUtils;

public class DocumentTypeServiceImpl implements DocumentTypeService {
	
	private static Logger logger = LogManager.getLogger(DocumentTypeServiceImpl.class);
	
	private DocumentTypeDAO documentTypeDAO;
	
	public DocumentTypeServiceImpl() {
		documentTypeDAO = new DocumentTypeDAOImpl();
	}

	@Override
	public Map<String, IDType> findAll() 
			throws ServiceException, DataException {
		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			return documentTypeDAO.findAll(conn);
			
		} catch (SQLException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		}
	}

}
