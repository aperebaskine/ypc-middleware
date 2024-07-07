package com.pinguela.yourpc.service;

import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.AttributeStatistics;
import com.pinguela.yourpc.model.ProductStatistics;

public interface ProductStatisticsService {
	
	public List<ProductStatistics> findByProduct(Date startDate, Date endDate, Long productId)
			throws ServiceException, DataException;
	
	public List<AttributeStatistics<?>> findByAttribute(Date startDate, Date endDate, Short categoryId, String attributeName)
			throws ServiceException, DataException;
	
	public List<ProductStatistics> findMostSold(Date startDate, Date endDate, Short categoryId)
			throws ServiceException, DataException;
	
	public List<ProductStatistics> findMostReturned(Date startDate, Date endDate, Short categoryId)
			throws ServiceException, DataException;

}
