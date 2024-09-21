package com.pinguela.yourpc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.dao.impl.TicketDAOImpl;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.util.HibernateUtils;

public class TicketServiceImpl 
implements TicketService {

	private static Logger logger = LogManager.getLogger(TicketServiceImpl.class);
	private TicketDAO ticketDAO = null;

	public TicketServiceImpl() {
		ticketDAO = new TicketDAOImpl();
	}

	@Override
	public Ticket findById(Long ticketId) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return ticketDAO.findById(session, ticketId);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Results<Ticket> findBy(TicketCriteria criteria, int pos, int pageSize) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return ticketDAO.findBy(session, criteria, pos, pageSize);
	        
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Long create(Ticket ticket) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        Long id = ticketDAO.create(session, ticket);
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
	public Boolean update(Ticket ticket) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return ticketDAO.update(session, ticket) && (commit = true);
	        
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

}
