package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.password.StrongPasswordEncryptor;

import com.pinguela.DataException;
import com.pinguela.InvalidLoginCredentialsException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.impl.EmployeeDAOImpl;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.service.EmployeeService;
import com.pinguela.yourpc.util.JDBCUtils;

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

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();			
			Employee e = employeeDAO.findByUsername(conn, username);
			
			if (e == null) throw new InvalidLoginCredentialsException();
			if (!PASSWORD_ENCRYPTOR.checkPassword(password, e.getEncryptedPassword())) {
				logger.warn("Error de autenticación del usuario: " +e.getUsername());
				throw new InvalidLoginCredentialsException();
			}
			logger.info("Empleado " +e.getUsername() +" autenticado con éxito.");
			return e;

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Integer register(Employee e) 
			throws ServiceException, DataException {
		
		if (e == null || e.getUsername() == null) {
			throw new IllegalArgumentException("Required parameters missing");
		}

		e.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(e.getUnencryptedPassword()));
		e.setUsername(e.getUsername().toLowerCase());
		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			Integer id = employeeDAO.create(conn, e);
			commit = id != null;
			return id;
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Employee findById(Integer employeeId) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return employeeDAO.findById(conn, employeeId);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Employee findByUsername(String username) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return employeeDAO.findByUsername(conn, username);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<Employee> findBy(EmployeeCriteria criteria)  
			throws ServiceException, DataException {
		
		if (criteria == null) {
			throw new IllegalArgumentException("Criteria cannot be null.");
		}

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return employeeDAO.findBy(conn, criteria);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Boolean update(Employee e)  
			throws ServiceException, DataException {
		
		if (e.getUnencryptedPassword() != null) {
			e.setEncryptedPassword(PASSWORD_ENCRYPTOR.encryptPassword(e.getUnencryptedPassword()));
			e.setUnencryptedPassword(null);	
		}

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (employeeDAO.update(conn, e)) {
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
	public Boolean updatePassword(Integer employeeId, String password) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			commit = employeeDAO.updatePassword(conn, employeeId, PASSWORD_ENCRYPTOR.encryptPassword(password));
			return true;

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean delete(Integer employeeId)  
			throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (employeeDAO.delete(conn, employeeId)) {
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
