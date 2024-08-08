package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.ProductStatisticsDAO;
import com.pinguela.yourpc.dao.impl.ProductStatisticsDAOImpl;
import com.pinguela.yourpc.model.AttributeStatistics;
import com.pinguela.yourpc.model.ProductStatistics;
import com.pinguela.yourpc.model.ProductStatisticsCriteria;
import com.pinguela.yourpc.service.ProductStatisticsService;
import com.pinguela.yourpc.util.JDBCUtils;

public class ProductStatisticsServiceImpl implements ProductStatisticsService {
	
	private static Logger logger = LogManager.getLogger(ProductStatisticsServiceImpl.class);
	
	private ProductStatisticsDAO productStatisticsDAO;
	
	public ProductStatisticsServiceImpl() {
		this.productStatisticsDAO = new ProductStatisticsDAOImpl();
	}

	@Override
	public List<ProductStatistics> findByProduct(Date startDate, Date endDate, Long productId) 
			throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return productStatisticsDAO.findByProduct(conn, startDate, endDate, productId);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<AttributeStatistics<?>> findByAttribute(Date startDate, Date endDate, Short categoryId, String attributeName)
			throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return productStatisticsDAO.findByAttribute(conn, startDate, endDate, categoryId, attributeName);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}
	
	@Override
	public List<ProductStatistics> getSalesStatistics(ProductStatisticsCriteria criteria)
			throws ServiceException, DataException {
		
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return productStatisticsDAO.getSalesStatistics(conn, criteria);

		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
