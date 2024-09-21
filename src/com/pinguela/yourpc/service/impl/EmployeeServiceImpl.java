package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.pinguela.DataException;
import com.pinguela.InvalidLoginCredentialsException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.impl.EmployeeDAOImpl;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.service.EmployeeService;
import com.pinguela.yourpc.util.HibernateUtils;

public class EmployeeServiceImpl implements EmployeeService {

	private static Logger logger = LogManager.getLogger(EmployeeServiceImpl.class);
	private static final StrongPasswordEncryptor PASSWORD_ENCRYPTOR = new StrongPasswordEncryptor();

	private EmployeeDAO employeeDAO = null;

	public EmployeeServiceImpl() {
		employeeDAO = new EmployeeDAOImpl();
	}

	@Override
	public Employee login(String username, String password) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        Employee e = employeeDAO.findByUsername(session, username);
	        
	        if (e == null) {
	        	throw new InvalidLoginCredentialsException();
	        }
	        
	        if (!PASSWORD_ENCRYPTOR.checkPassword(password, e.getEncryptedPassword())) {
	            logger.warn("Could not authenticate user {}.", e.getUsername());
	            throw new InvalidLoginCredentialsException();
	        }
	        
	        logger.info("User {} authenticated successfully.", e.getUsername());
	        return e;

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Integer register(Employee e) 
	        throws ServiceException, DataException {

	    if (e == null) {
	        throw new IllegalArgumentException("Employee cannot be null.");
	    }

	    // TODO: Improve password generation
	    if (e.getUnencryptedPassword() == null) {
	        e.setUnencryptedPassword(String.valueOf(System.currentTimeMillis()));
	    }

	    e.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(e.getUnencryptedPassword()));
	    e.setUsername(e.getUsername().toLowerCase());
	    
	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        Integer id = employeeDAO.create(session, e);
	        commit = id != null;
	        return id;
	        
	    } catch (HibernateException he) {
	        logger.fatal(he.getMessage(), he);
	        throw new ServiceException(he);
	    } finally {
	    	e.setUnencryptedPassword(null);
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Employee findById(Integer employeeId) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return employeeDAO.findById(session, employeeId);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Employee findByUsername(String username) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return employeeDAO.findByUsername(session, username);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public List<Employee> findBy(EmployeeCriteria criteria)  
	        throws ServiceException, DataException {
	    
	    if (criteria == null) {
	        throw new IllegalArgumentException("Criteria cannot be null.");
	    }

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return employeeDAO.findBy(session, criteria);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Boolean update(Employee e)  
	        throws ServiceException, DataException {

	    if (e.getUnencryptedPassword() != null) {
	        e.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(e.getUnencryptedPassword()));
	        e.setUnencryptedPassword(null);    
	    }

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return employeeDAO.update(session, e) && (commit = true);
	        
	    } catch (HibernateException he) {
	        logger.fatal(he.getMessage(), he);
	        throw new ServiceException(he);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Boolean updatePassword(Integer employeeId, String password) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return employeeDAO.updatePassword(session, employeeId, 
	        		PASSWORD_ENCRYPTOR.encryptPassword(password)) && (commit = true);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Boolean delete(Integer employeeId)  
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        return employeeDAO.delete(session, employeeId) && (commit = true);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

}
