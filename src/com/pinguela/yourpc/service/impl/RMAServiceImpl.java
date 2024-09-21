package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.dao.impl.RMADAOImpl;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.service.RMAService;
import com.pinguela.yourpc.util.HibernateUtils;

public class RMAServiceImpl 
implements RMAService {
	
	private static Logger logger = LogManager.getLogger(RMAServiceImpl.class);
	private RMADAO rmaDAO = null;
	
	public RMAServiceImpl() {
		rmaDAO = new RMADAOImpl();
	}

	@Override
	public RMA findById(Long rmaId) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return rmaDAO.findById(session, rmaId);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public List<RMA> findBy(RMACriteria criteria) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return rmaDAO.findBy(session, criteria);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Long create(RMA rma) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        Long id = rmaDAO.create(session, rma);
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
	public Boolean update(RMA rma) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return rmaDAO.update(session, rma) && (commit = true);
	        
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

}
