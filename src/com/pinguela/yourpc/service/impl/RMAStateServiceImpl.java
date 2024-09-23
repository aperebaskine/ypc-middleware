package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.RMAStateDAO;
import com.pinguela.yourpc.dao.impl.RMAStateDAOImpl;
import com.pinguela.yourpc.model.RMAState;
import com.pinguela.yourpc.service.RMAStateService;
import com.pinguela.yourpc.util.HibernateUtils;

public class RMAStateServiceImpl implements RMAStateService {
	
	private static Logger logger = LogManager.getLogger(RMAStateServiceImpl.class);
	
	private RMAStateDAO rmaStateDAO;
	
	public RMAStateServiceImpl() {
		rmaStateDAO = new RMAStateDAOImpl();
	}
	
	@Override
	public Map<String, RMAState> findAll() 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return rmaStateDAO.findAll(session);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

}
