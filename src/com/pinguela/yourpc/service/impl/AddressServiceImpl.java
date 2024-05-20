package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.dao.impl.AddressDAOImpl;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.service.AddressService;
import com.pinguela.yourpc.util.JDBCUtils;

public class AddressServiceImpl implements AddressService {

	private static Logger logger = LogManager.getLogger(AddressServiceImpl.class);
	private AddressDAO addressDAO = null;

	public AddressServiceImpl() {
		addressDAO = new AddressDAOImpl();
	}

	@Override
	public Address findById(Integer id) 
			throws ServiceException, DataException {

		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			return addressDAO.findById(conn, id);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Address findByEmployee(Integer employeeId) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return addressDAO.findByEmployee(conn, employeeId);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<Address> findByCustomer(Integer customerId) 
			throws ServiceException, DataException {

		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			return addressDAO.findByCustomer(conn, customerId);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Integer create(Address a) 
			throws ServiceException, DataException {
		
		if (a == null) {
			throw new ServiceException(ErrorCodes.NULL_REQUIRED_PARAMETER);
		}

		Connection conn = null;
		Integer id = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			
			id = addressDAO.create(conn, a);
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
	public Integer update(Address a) 
			throws ServiceException, DataException {
		
		if (a == null) {
			throw new ServiceException(ErrorCodes.NULL_REQUIRED_PARAMETER);
		}

		Connection conn = null;
		Integer id = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			
			id = addressDAO.update(conn, a);
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
	public Boolean delete(Integer id) 
			throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			
			if (addressDAO.delete(conn, id)) {
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
	public Boolean deleteByCustomer(Integer customerId) 
			throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			
			if (addressDAO.deleteByCustomer(conn, customerId)) {
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
