package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AttributeStatistics;
import com.pinguela.yourpc.model.ProductStatistics;
import com.pinguela.yourpc.model.ProductStatisticsCriteria;

public interface ProductStatisticsDAO {
	
	public List<ProductStatistics> getSalesStatistics(Connection conn, ProductStatisticsCriteria criteria)
			throws DataException;
	
	public List<ProductStatistics> findByProduct(Connection conn, Date startDate, Date endDate, Long productId)
			throws DataException;
	
	public List<AttributeStatistics<?>> findByAttribute(Connection conn, Date startDate, Date endDate, Short categoryId, String attributeName)
			throws DataException;

}
