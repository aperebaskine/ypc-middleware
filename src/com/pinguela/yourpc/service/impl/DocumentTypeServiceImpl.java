package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.DocumentTypeDAO;
import com.pinguela.yourpc.dao.impl.DocumentTypeDAOImpl;
import com.pinguela.yourpc.model.IDType;
import com.pinguela.yourpc.service.DocumentTypeService;
import com.pinguela.yourpc.util.HibernateUtils;

public class DocumentTypeServiceImpl implements DocumentTypeService {
	
	private static Logger logger = LogManager.getLogger(DocumentTypeServiceImpl.class);
	
	private DocumentTypeDAO documentTypeDAO;
	
	public DocumentTypeServiceImpl() {
		documentTypeDAO = new DocumentTypeDAOImpl();
	}

	@Override
	public Map<String, IDType> findAll() 
			throws ServiceException, DataException {
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return documentTypeDAO.findAll(session);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}
	
}
