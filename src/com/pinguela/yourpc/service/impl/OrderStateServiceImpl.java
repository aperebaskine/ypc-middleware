package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.OrderStateDAO;
import com.pinguela.yourpc.dao.impl.OrderStateDAOImpl;
import com.pinguela.yourpc.model.OrderState;
import com.pinguela.yourpc.service.OrderStateService;
import com.pinguela.yourpc.util.HibernateUtils;

public class OrderStateServiceImpl implements OrderStateService {
	
	private static Logger logger = LogManager.getLogger(OrderStateServiceImpl.class);
	
	private OrderStateDAO orderStateDAO;
	
	public OrderStateServiceImpl() {
		orderStateDAO = new OrderStateDAOImpl();
	}

	@Override
	public Map<String, OrderState> findAll() 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return orderStateDAO.findAll(session);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}


}
