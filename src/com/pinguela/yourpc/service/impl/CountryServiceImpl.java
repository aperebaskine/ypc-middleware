package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CountryDAO;
import com.pinguela.yourpc.dao.impl.CountryDAOImpl;
import com.pinguela.yourpc.model.Country;
import com.pinguela.yourpc.service.CountryService;
import com.pinguela.yourpc.util.JDBCUtils;

public class CountryServiceImpl implements CountryService {
	
	private static Logger logger = LogManager.getLogger(CountryServiceImpl.class);
	private CountryDAO countryDAO = null;
	
	public CountryServiceImpl() {
		countryDAO = new CountryDAOImpl();
	}

	@Override
	public List<Country> findAll() 
			throws ServiceException, DataException {
		
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return countryDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
