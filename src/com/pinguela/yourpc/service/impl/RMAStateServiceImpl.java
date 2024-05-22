package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.RMAStateDAO;
import com.pinguela.yourpc.dao.impl.RMAStateDAOImpl;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.service.RMAStateService;
import com.pinguela.yourpc.util.JDBCUtils;

public class RMAStateServiceImpl implements RMAStateService {
	
	private static Logger logger = LogManager.getLogger(RMAStateServiceImpl.class);
	
	private RMAStateDAO rmaStateDAO;
	
	public RMAStateServiceImpl() {
		rmaStateDAO = new RMAStateDAOImpl();
	}
	
	@Override
	public Map<String, ItemState<RMA>> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return rmaStateDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
