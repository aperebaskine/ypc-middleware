package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.OrderStateDAO;
import com.pinguela.yourpc.dao.impl.OrderStateDAOImpl;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.service.OrderStateService;
import com.pinguela.yourpc.util.JDBCUtils;

public class OrderStateServiceImpl implements OrderStateService {
	
	private static Logger logger = LogManager.getLogger(OrderStateServiceImpl.class);
	
	private OrderStateDAO orderStateDAO;
	
	public OrderStateServiceImpl() {
		orderStateDAO = new OrderStateDAOImpl();
	}

	@Override
	public Map<String, EntityState<CustomerOrder>> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return orderStateDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
