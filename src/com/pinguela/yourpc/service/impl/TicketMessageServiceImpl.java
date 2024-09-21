package com.pinguela.yourpc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.dao.impl.TicketMessageDAOImpl;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.service.TicketMessageService;
import com.pinguela.yourpc.util.HibernateUtils;

public class TicketMessageServiceImpl 
implements TicketMessageService {
	
	private static Logger logger = LogManager.getLogger(TicketMessageServiceImpl.class);
	private TicketMessageDAO ticketMessageDAO = null;
	
	public TicketMessageServiceImpl() {
		ticketMessageDAO = new TicketMessageDAOImpl();
	}

	@Override
	public Long create(TicketMessage ticketMessage) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        Long id = ticketMessageDAO.create(session, ticketMessage);
	        commit = id != null;
	        return id;
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Boolean delete(Long messageId) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return ticketMessageDAO.delete(session, messageId) && (commit = true);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

}
