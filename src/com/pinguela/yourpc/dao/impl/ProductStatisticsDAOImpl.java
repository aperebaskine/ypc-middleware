package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.ProductStatisticsDAO;
import com.pinguela.yourpc.model.AttributeStatistics;
import com.pinguela.yourpc.model.ProductStatistics;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

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
	
	private static final String FINDMOST_QUERY_PLACEHOLDER = 
			" SELECT p.ID, p.NAME, sum(ol.quantity), sum(rol.quantity), sum(rol.quantity) / sum(ol.quantity) * 100 return_pct, avg(ol.purchase_price), avg(ol.sale_price) " +
            " FROM PRODUCT p " +
            " INNER JOIN ORDER_LINE ol " +
            " ON p.ID = ol.PRODUCT_ID " +
            " INNER JOIN CUSTOMER_ORDER CO " +
            " ON co.ID = ol.CUSTOMER_ORDER_ID " +
            " LEFT JOIN RMA_ORDER_LINE rol " +
            " ON rol.ORDER_LINE_ID = ol.ID " +
            " WHERE CAST(co.ORDER_DATE AS DATE) >= ? " +
            " AND CAST(co.ORDER_DATE AS DATE) <= ? ";
	
	private static final String FINDMOST_QUERY_PLACEHOLDER_GROUPBY =
            " GROUP BY p.ID"
            + " ORDER BY %s DESC";

	public static final String FINDBYATTRIBUTE_PLACEHOLDER_QUERY =
		    " SELECT COUNT(at.ID), at.NAME, at.ATTRIBUTE_DATA_TYPE_ID, av.VALUE_BIGINT, av.VALUE_VARCHAR, av.VALUE_DECIMAL, av.VALUE_BOOLEAN " +
		    " FROM ATTRIBUTE_TYPE at " +
		    " INNER JOIN CATEGORY_ATTRIBUTE_TYPE cat " +
		    " ON cat.ATTRIBUTE_TYPE_ID = at.ID " +
		    " INNER JOIN ATTRIBUTE_VALUE av " +
		    " ON at.ID = av.ATTRIBUTE_TYPE_ID " +
		    " INNER JOIN PRODUCT_ATTRIBUTE_VALUE pav " +
		    " ON av.ID = pav.ATTRIBUTE_VALUE_ID " +
		    " INNER JOIN PRODUCT p " +
		    " ON p.ID = pav.PRODUCT_ID " +
		    " INNER JOIN ORDER_LINE ol " +
		    " ON ol.PRODUCT_ID = p.ID " +
		    " INNER JOIN CUSTOMER_ORDER co " +
		    " ON co.ID = ol.CUSTOMER_ORDER_ID " +
		    " AND CAST(co.ORDER_DATE AS DATE) >= ? " +
		    " AND CAST(co.ORDER_DATE AS DATE) <= ? " +
		    " WHERE at.NAME = ? " +
		    " AND p.CATEGORY_ID%s" +
		    " GROUP BY at.NAME, av.VALUE_BIGINT, av.VALUE_VARCHAR, av.VALUE_DECIMAL, av.VALUE_BOOLEAN " +
		    " ORDER BY at.NAME ASC, av.VALUE_BIGINT ASC, av.VALUE_VARCHAR ASC, av.VALUE_DECIMAL ASC, av.VALUE_BOOLEAN ASC; ";

	
	private static Logger logger = LogManager.getLogger(ProductStatisticsDAOImpl.class);

	@Override
	public List<ProductStatistics> findByProduct(Connection conn, Date startDate, Date endDate, Long productId)
			throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProductStatistics> results = new ArrayList<ProductStatistics>();
		
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
	
	@Override
	public List<ProductStatistics> findMostSold(Connection conn, Date startDate, Date endDate, Short categoryId)
			throws DataException {
		StringBuilder query = new StringBuilder(FINDMOST_QUERY_PLACEHOLDER);

		if (categoryId != null) {	
			String categoryCondition = 
					new StringBuilder(" AND p.CATEGORY_ID")
					.append(SQLQueryUtils.buildPlaceholderComparisonClause(
							CategoryUtils.getLowerHierarchy(categoryId).keySet())
							)
					.toString();
			query.append(categoryCondition);
		}
		
		query.append(String.format(FINDMOST_QUERY_PLACEHOLDER_GROUPBY, "sum(ol.quantity)"));
		return findMost(conn, query.toString(), startDate, endDate, categoryId);
	}
	
	@Override
	public List<ProductStatistics> findMostReturned(Connection conn, Date startDate, Date endDate, Short categoryId)
			throws DataException {
		StringBuilder query = new StringBuilder(FINDMOST_QUERY_PLACEHOLDER);

		if (categoryId != null) {	
			String categoryCondition = 
					new StringBuilder(" AND p.CATEGORY_ID")
					.append(SQLQueryUtils.buildPlaceholderComparisonClause(
							CategoryUtils.getLowerHierarchy(categoryId).keySet())
							)
					.toString();
			query.append(categoryCondition);
		}
		
		query.append(String.format(FINDMOST_QUERY_PLACEHOLDER_GROUPBY, "return_pct"));
		return findMost(conn, query.toString(), startDate, endDate, categoryId);
	}
	
	private List<ProductStatistics> findMost(Connection conn, String query, Date startDate, Date endDate, Short categoryId)
			throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<ProductStatistics> results = new ArrayList<ProductStatistics>();
		
		try {
			stmt = conn.prepareStatement(query);
			
			int i = 1;
			stmt.setDate(i++, new java.sql.Date(startDate.getTime()));
			stmt.setDate(i++, new java.sql.Date(endDate.getTime()));
			if (categoryId != null) {	
				for (Short parameter : CategoryUtils.getLowerHierarchy(categoryId).keySet()) {
					stmt.setShort(i++, parameter);
				}
			}
			
			rs = stmt.executeQuery();
			
			int pos = 0;
			while (rs.next() && pos++ < 16) {
				ProductStatistics dto = new ProductStatistics();
				
				int j = 1;
				dto.setProductId(rs.getLong(j++));
				dto.setProductName(rs.getString(j++));
				dto.setQuantitySold(rs.getInt(j++));
				dto.setQuantityReturned(rs.getInt(j++));
				dto.setPctReturned(rs.getDouble(j++));
				dto.setAvgPurchasePrice(rs.getDouble(j++));
				dto.setAvgSalePrice(rs.getDouble(j++));
				
				results.add(dto);
			}
			return results;
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e.getMessage(), e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}	
	}
	
	@Override
	public List<AttributeStatistics<?>> findByAttribute(Connection conn, Date startDate, Date endDate,
			Short categoryId, String attributeName) throws DataException {
		
		Set<Short> categoryIds = CategoryUtils.getLowerHierarchy(categoryId).keySet();
		String query = String.format(FINDBYATTRIBUTE_PLACEHOLDER_QUERY, SQLQueryUtils.buildPlaceholderComparisonClause(categoryIds.size()));

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<AttributeStatistics<?>> results = new ArrayList<AttributeStatistics<?>>();
		
		try {
			stmt = conn.prepareStatement(query);
			
			int i = 1;
			stmt.setDate(i++, new java.sql.Date(startDate.getTime()));
			stmt.setDate(i++, new java.sql.Date(endDate.getTime()));
			stmt.setString(i++, attributeName);
			if (categoryId != null) {	
				for (Short parameter : categoryIds) {
					stmt.setShort(i++, parameter);
				}
			}
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int j = 1;
				Integer quantity = rs.getInt(j++);
				String name = rs.getString(j++);
				String dataType = rs.getString(j++);				
				Object value = rs.getObject(AttributeUtils.getValueColumnName(dataType), AttributeDTO.TYPE_PARAMETER_CLASSES.get(dataType));
				
				results.add(new AttributeStatistics<>(name, value, quantity));
			}
			return results;
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e.getMessage(), e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}	
	}
	
	private ProductStatistics loadNext(ResultSet rs) throws SQLException {
		ProductStatistics dto = new ProductStatistics();
		
		int i = 1;
		dto.setDate(rs.getDate(i++));
		dto.setQuantitySold(rs.getInt(i++));
		dto.setQuantityReturned(rs.getInt(i++));
		dto.setAvgSalePrice(rs.getDouble(i++));
		
		return dto;
	}

}
