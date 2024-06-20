package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AttributeStatisticsDTO;
import com.pinguela.yourpc.model.ProductStatisticsDTO;

public interface ProductStatisticsDAO {
	
	public List<ProductStatisticsDTO> findByProduct(Connection conn, Date startDate, Date endDate, Long productId)
			throws DataException;
	
	public List<AttributeStatisticsDTO<?>> findByAttribute(Connection conn, Date startDate, Date endDate, Short categoryId, String attributeName)
			throws DataException;
	
	public List<ProductStatisticsDTO> findMostSold(Connection conn, Date startDate, Date endDate, Short categoryId)
			throws DataException;
	
	public List<ProductStatisticsDTO> findMostReturned(Connection conn, Date startDate, Date endDate, Short categoryId)
			throws DataException;

}
