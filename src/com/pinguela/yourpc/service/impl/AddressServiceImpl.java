package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.dao.impl.AddressDAOImpl;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.service.AddressService;
import com.pinguela.yourpc.util.HibernateUtils;

public class AddressServiceImpl implements AddressService {

	private static Logger logger = LogManager.getLogger(AddressServiceImpl.class);
	private AddressDAO addressDAO = null;

	public AddressServiceImpl() {
		addressDAO = new AddressDAOImpl();
	}

	@Override
	public Address findById(Integer id) 
			throws ServiceException, DataException {

		Session session = null;
		
		try {
			session = HibernateUtils.openSession();
			return addressDAO.findById(session, id);
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public Address findByEmployee(Integer employeeId) 
			throws ServiceException, DataException {

		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return addressDAO.findByEmployee(session, employeeId);
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public List<Address> findByCustomer(Integer customerId) 
			throws ServiceException, DataException {

		Session session = null;
		
		try {
			session = HibernateUtils.openSession();
			return addressDAO.findByCustomer(session, customerId);
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public Integer create(Address a) 
			throws ServiceException, DataException {
		
		Session session = null;
		Transaction transaction = null;

		Integer id = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			id = addressDAO.create(session, a);
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
	public Integer update(Address a) 
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		
		Integer id = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			id = addressDAO.update(session, a);
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
	public Boolean delete(Integer id) 
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			return addressDAO.delete(session, id) && (commit = true);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}

	@Override
	public Boolean deleteByCustomer(Integer customerId) 
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			return addressDAO.deleteByCustomer(session, customerId) && (commit = true);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}

}
