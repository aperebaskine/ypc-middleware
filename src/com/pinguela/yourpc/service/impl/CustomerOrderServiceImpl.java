package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.dao.impl.CustomerOrderDAOImpl;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.service.CustomerOrderService;
import com.pinguela.yourpc.util.JDBCUtils;

public class CustomerOrderServiceImpl implements CustomerOrderService {

	private static Logger logger = LogManager.getLogger(CustomerOrderServiceImpl.class);
	private CustomerOrderDAO customerOrderDAO = null;

	public CustomerOrderServiceImpl() {
		customerOrderDAO = new CustomerOrderDAOImpl();
	}

	@Override
	public Long create(CustomerOrder co) 
			throws ServiceException, DataException {
		
		if (co.getCustomerId() == null || co.getTotalPrice() == null
				|| co.getBillingAddressId() == null || co.getShippingAddressId() == null
				|| co.getOrderLines() == null || co.getOrderLines().isEmpty()) {
			throw new IllegalArgumentException("Required parameters missing");
		}
		
		co.setTotalPrice(calculateTotalPrice(co));

		Connection conn = null;
		boolean commit = false;
		Long id = null;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			id = customerOrderDAO.create(conn, co);
			commit = id != null;
			return id;
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}
	
	private double calculateTotalPrice(CustomerOrder co) {

		double totalPrice = 0.0d;
		for (OrderLine ol : co.getOrderLines()) {
			totalPrice += (ol.getSalePrice()*ol.getQuantity());
		}
		return totalPrice;
	}

	@Override
	public Boolean update(CustomerOrder po) 
			throws ServiceException, DataException {
		
		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (customerOrderDAO.update(conn, po)) {
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
	public CustomerOrder findById(Long id) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return customerOrderDAO.findById(conn, id);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<CustomerOrder> findBy(CustomerOrderCriteria criteria) 
			throws ServiceException, DataException {
		
		if (criteria == null) {
			throw new IllegalArgumentException("Required parameter is missing.");
		}

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return customerOrderDAO.findBy(conn, criteria);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
