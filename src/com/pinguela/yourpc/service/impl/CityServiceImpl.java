package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CityDAO;
import com.pinguela.yourpc.dao.impl.CityDAOImpl;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.service.CityService;
import com.pinguela.yourpc.util.JDBCUtils;

public class CityServiceImpl implements CityService {
	
	private static Logger logger = LogManager.getLogger(CityServiceImpl.class);
	private CityDAO cityDAO = null;
	
	public CityServiceImpl() {
		cityDAO = new CityDAOImpl();
	}

	@Override
	public List<City> findByProvince(Integer provinceId) 
			throws ServiceException, DataException {
		
		if (provinceId == null) {
			return null;
		}

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return cityDAO.findByProvince(conn, provinceId);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
