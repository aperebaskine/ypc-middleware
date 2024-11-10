package com.pinguela.yourpc.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import com.pinguela.yourpc.util.JDBCUtils;

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

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			Customer c = customerDAO.findByEmail(conn, email);
			if (c == null) throw new InvalidLoginCredentialsException();

			if (!PASSWORD_ENCRYPTOR.checkPassword(password, c.getEncryptedPassword())) {
				logger.warn("Error de autenticación del usuario {}.", c.getEmail());
				throw new InvalidLoginCredentialsException();
			}

			logger.info("Usuario {} autenticado con éxito.", c.getEmail());
			return c;

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

//	private static final String EMAIL_SUBJECT = "Registración en YourPC confirmada!";
//	private static final String REGISTRATION_MESSAGE;
//	
//	static {
//		InputStream inputStream = ClassLoader.getSystemResourceAsStream("email.html");
//		StringBuilder stringBuilder = new StringBuilder();
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                stringBuilder.append(line).append("\n");
//            }
//        } catch (IOException e) {
//        	logger.fatal(e.getMessage(), e);
//        	throw new ExceptionInInitializerError();
//        }
//        REGISTRATION_MESSAGE = stringBuilder.toString();
//	}

	// TODO: Use customer email instead of temporary test email
	private static final String TEST_EMAIL = "pereb_test@outlook.com";

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

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			Integer id = customerDAO.create(conn, c);
			commit = (id != null);
//			mailService.send(EMAIL_SUBJECT, String.format(REGISTRATION_MESSAGE, 
//					c.getFirstName(), c.getEmail(), c.getUnencryptedPassword()), TEST_EMAIL);

			return id;
//		} catch (MailException me) {
//			logger.error(me);
//			throw new ServiceException(me);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			c.setUnencryptedPassword(null);
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Customer findById(Integer customerId) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return customerDAO.findById(conn, customerId);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Customer findByEmail(String email)
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return customerDAO.findByEmail(conn, email);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<Customer> findBy(CustomerCriteria criteria) 
			throws ServiceException, DataException {
		
		if (criteria == null) {
			throw new IllegalArgumentException("Criteria cannot be null.");
		}

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return customerDAO.findBy(conn, criteria);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Boolean update(Customer c) 
			throws ServiceException, DataException {

		if (c.getUnencryptedPassword() != null) {
			c.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(c.getUnencryptedPassword()));
			c.setUnencryptedPassword(null);
		}

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (customerDAO.update(conn, c)) {
				commit = true;
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean updatePassword(Integer customerId, String password) 
			throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (customerDAO.updatePassword(conn, customerId, PASSWORD_ENCRYPTOR.encryptPassword(password))) {
				commit = true;
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean delete(Integer customerId) 
			throws ServiceException, DataException {
		
		if (customerId == null) {
			throw new ServiceException(ErrorCodes.NULL_REQUIRED_PARAMETER);
		}

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (customerDAO.delete(conn, customerId)) {
				commit = true;
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);

		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

}
