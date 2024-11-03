package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.impl.AttributeDAOImpl;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.util.HibernateUtils;

public class AttributeServiceImpl implements AttributeService {
	
	private static Logger logger = LogManager.getLogger(AttributeServiceImpl.class);
	private AttributeDAO attributeDAO = null;
	
	public AttributeServiceImpl() {
		attributeDAO = new AttributeDAOImpl();
	}
	
	@Override
	public Attribute<?> findByName(String name, boolean returnUnassigned) 
			throws ServiceException, DataException {
		
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return attributeDAO.findByName(session, name, returnUnassigned);
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public Map<String, Attribute<?>> findByCategory(Short categoryId, boolean returnUnassigned)
			throws ServiceException, DataException {
		
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return attributeDAO.findByCategory(session, categoryId, returnUnassigned);
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}		
	}

}
