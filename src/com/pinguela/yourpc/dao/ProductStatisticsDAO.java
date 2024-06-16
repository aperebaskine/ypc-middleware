package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ProductStatisticsDTO;

public interface ProductStatisticsDAO {
	
	public List<ProductStatisticsDTO> findByProduct(Connection conn, Date startDate, Date endDate, Long productId)
			throws DataException;

}
