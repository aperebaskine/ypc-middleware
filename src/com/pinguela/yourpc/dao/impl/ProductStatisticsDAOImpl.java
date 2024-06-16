package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.ProductStatisticsDAO;
import com.pinguela.yourpc.model.ProductStatisticsDTO;
import com.pinguela.yourpc.util.JDBCUtils;

public class ProductStatisticsDAOImpl 
implements ProductStatisticsDAO {
	
	private static final String FINDBYPRODUCT_QUERY =
			" SELECT CAST(co.ORDER_DATE AS DATE) AS DATE, SUM(ol.QUANTITY), SUM(rol.QUANTITY), ol.SALE_PRICE"
			+ " FROM ORDER_LINE ol"
			+ " INNER JOIN CUSTOMER_ORDER co"
			+ " ON ol.CUSTOMER_ORDER_ID = co.ID"
			+ " LEFT JOIN RMA_ORDER_LINE rol"
			+ " ON rol.ORDER_LINE_ID = ol.ID"
			+ " WHERE ol.PRODUCT_id = ?"
			+ " AND CAST(co.ORDER_DATE AS DATE) >= ?"
			+ " AND CAST(co.ORDER_DATE AS DATE) <= ?"
			+ " GROUP BY DATE, ol.SALE_PRICE"
			+ " ORDER BY DATE ASC, ol.SALE_PRICE ASC";
	
	private static Logger logger = LogManager.getLogger(ProductStatisticsDAOImpl.class);

	@Override
	public List<ProductStatisticsDTO> findByProduct(Connection conn, Date startDate, Date endDate, Long productId)
			throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProductStatisticsDTO> results = new ArrayList<ProductStatisticsDTO>();
		
		try {
			stmt = conn.prepareStatement(FINDBYPRODUCT_QUERY);
			
			int i = 1;
			stmt.setLong(i++, productId);
			stmt.setDate(i++, new java.sql.Date(startDate.getTime()));
			stmt.setDate(i++, new java.sql.Date(endDate.getTime()));
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e.getMessage(), e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}	
	}
	
	private ProductStatisticsDTO loadNext(ResultSet rs) throws SQLException {
		ProductStatisticsDTO dto = new ProductStatisticsDTO();
		
		int i = 1;
		dto.setDate(rs.getDate(i++));
		dto.setQuantitySold(rs.getInt(i++));
		dto.setQuantityReturned(rs.getInt(i++));
		dto.setAvgPrice(rs.getDouble(i++));
		
		return dto;
	}

}
