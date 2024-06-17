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
import com.pinguela.yourpc.model.ProductStatisticsDTO;
import com.pinguela.yourpc.service.ProductStatisticsService;
import com.pinguela.yourpc.util.JDBCUtils;

public class ProductStatisticsServiceImpl implements ProductStatisticsService {
	
	private static Logger logger = LogManager.getLogger(ProductStatisticsServiceImpl.class);
	
	private ProductStatisticsDAO productStatisticsDAO;
	
	public ProductStatisticsServiceImpl() {
		this.productStatisticsDAO = new ProductStatisticsDAOImpl();
	}

	@Override
	public List<ProductStatisticsDTO> findByProduct(Date startDate, Date endDate, Long productId) 
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

}
