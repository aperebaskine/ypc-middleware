package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.InvalidLoginCredentialsException;
import com.pinguela.MailException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.dao.impl.CustomerDAOImpl;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.service.CustomerService;
import com.pinguela.yourpc.service.MailService;
import com.pinguela.yourpc.util.EmailUtils;
import com.pinguela.yourpc.util.HibernateUtils;

public class CustomerServiceImpl implements CustomerService {

	private static Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	private static final StrongPasswordEncryptor PASSWORD_ENCRYPTOR = new StrongPasswordEncryptor();

	private CustomerDAO customerDAO = null;
	private MailService mailService = null;

	public CustomerServiceImpl() {
		customerDAO = new CustomerDAOImpl();
		mailService = new MailServiceImpl();
	}

	@Override
	public Customer login(String email, String password) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        Customer c = customerDAO.findByEmail(session, email);
	        
	        if (c == null) {
	            throw new InvalidLoginCredentialsException();
	        }

	        if (!PASSWORD_ENCRYPTOR.checkPassword(password, c.getEncryptedPassword())) {
	            logger.warn("Could not authenticate user {}.", c.getEmail());
	            throw new InvalidLoginCredentialsException();
	        }

	        logger.info("User {} authenticated successfully.", c.getEmail());
	        return c;

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Integer register(Customer c) 
	        throws ServiceException, DataException {
	    
	    if (c == null) {
	        throw new IllegalArgumentException("Customer cannot be null.");
	    }

	    if (c.getUnencryptedPassword() == null) {
	        c.setUnencryptedPassword(String.valueOf(System.currentTimeMillis()));
	    }
	    
	    c.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(c.getUnencryptedPassword()));
	    c.setEmail(c.getEmail().toLowerCase());

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        
	        Integer id = customerDAO.create(session, c);
	        commit = (id != null);
	    	// TODO: Use customer email instead of temporary test email
	        mailService.send(EmailUtils.EMAIL_SUBJECT, String.format(EmailUtils.REGISTRATION_MESSAGE, 
	                c.getName().getFirstName(), c.getEmail(), c.getUnencryptedPassword()), EmailUtils.TEST_EMAIL);

	        return id;
	    } catch (MailException me) {
	        logger.error(me.getMessage(), me);
	        throw new ServiceException(me);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        c.setUnencryptedPassword(null);
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Customer findById(Integer customerId) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return customerDAO.findById(session, customerId);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Customer findByEmail(String email)
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return customerDAO.findByEmail(session, email);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public List<Customer> findBy(CustomerCriteria criteria) 
	        throws ServiceException, DataException {
	    
	    if (criteria == null) {
	        throw new IllegalArgumentException("Criteria cannot be null.");
	    }

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return customerDAO.findBy(session, criteria);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

	@Override
	public Boolean update(Customer c) 
	        throws ServiceException, DataException {

	    if (c.getUnencryptedPassword() != null) {
	        c.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(c.getUnencryptedPassword()));
	        c.setUnencryptedPassword(null);
	    }

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        
	        commit = customerDAO.update(session, c);
	        return commit;
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Boolean updatePassword(Integer customerId, String password) 
	        throws ServiceException, DataException {

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        
	        return customerDAO.updatePassword(session, customerId, 
	        		PASSWORD_ENCRYPTOR.encryptPassword(password)) && (commit = true);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

	@Override
	public Boolean delete(Integer customerId) 
	        throws ServiceException, DataException {
	    
	    if (customerId == null) {
	        throw new ServiceException(ErrorCodes.NULL_REQUIRED_PARAMETER);
	    }

	    Session session = null;
	    Transaction transaction = null;
	    boolean commit = false;

	    try {
	        session = HibernateUtils.openSession();
	        transaction = session.beginTransaction();
	        
	        return customerDAO.delete(session, customerId) && (commit = true);
	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session, transaction, commit);
	    }
	}

}
