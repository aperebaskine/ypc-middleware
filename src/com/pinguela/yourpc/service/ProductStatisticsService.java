package com.pinguela.yourpc.service;

import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.AttributeStatistics;
import com.pinguela.yourpc.model.ProductStatistics;
import com.pinguela.yourpc.model.ProductStatisticsCriteria;

public interface ProductStatisticsService {
	
	public List<ProductStatistics> getSalesStatistics(ProductStatisticsCriteria criteria)
			throws ServiceException, DataException;
	
	public List<ProductStatistics> findByProduct(Date startDate, Date endDate, Long productId)
			throws ServiceException, DataException;
	
	public List<AttributeStatistics<?>> findByAttribute(Date startDate, Date endDate, Short categoryId, String attributeName)
			throws ServiceException, DataException;

}
