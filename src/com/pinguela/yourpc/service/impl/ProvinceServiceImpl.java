package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.ProvinceDAO;
import com.pinguela.yourpc.dao.impl.ProvinceDAOImpl;
import com.pinguela.yourpc.model.Province;
import com.pinguela.yourpc.service.ProvinceService;
import com.pinguela.yourpc.util.JDBCUtils;

public class ProvinceServiceImpl implements ProvinceService {
	
	private static Logger logger = LogManager.getLogger(ProvinceServiceImpl.class);
	private ProvinceDAO provinceDAO = null;
	
	public ProvinceServiceImpl() {
		provinceDAO = new ProvinceDAOImpl();
	}

	@Override
	public List<Province> findByCountry(String countryId)  
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return provinceDAO.findByCountry(conn, countryId);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
