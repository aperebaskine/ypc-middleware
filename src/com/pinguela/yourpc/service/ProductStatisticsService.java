package com.pinguela.yourpc.service;

import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ProductStatisticsDTO;

public interface ProductStatisticsService {
	
	public List<ProductStatisticsDTO> findByProduct(Date startDate, Date endDate, Long productId)
			throws ServiceException, DataException;

}
