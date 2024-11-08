package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.dto.CategoryDTO;
import com.pinguela.yourpc.util.JDBCUtils;

public class CategoryDAOImpl implements CategoryDAO {
	
	private static final String BASE_QUERY = 
			" SELECT c.ID, cl.NAME, c.PARENT_ID"
			+ " FROM CATEGORY c"
			+ " INNER JOIN CATEGORY_LOCALE cl"
			+ " ON cl.CATEGORY_ID = c.ID"
			+ " AND cl.LOCALE = ?";
	
	private static final String WHERE_PARENT_ID_EQUALS = " WHERE c.PARENT_ID = ?";
	private static final String WHERE_PARENT_ID_IS_NULL = " WHERE c.PARENT_ID IS NULL";

	private static final String FIND_ROOT_CATEGORIES_QUERY = 
			BASE_QUERY +WHERE_PARENT_ID_IS_NULL;
	private static final String FIND_CHILDREN_QUERY = 
			BASE_QUERY +WHERE_PARENT_ID_EQUALS;

	private static Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

	@Override
	public Map<Short, CategoryDTO> findAll(Connection conn, Locale locale) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<Short, CategoryDTO> results = new TreeMap<Short, CategoryDTO>();

		try {
			stmt = conn.prepareStatement(FIND_ROOT_CATEGORIES_QUERY);
			stmt.setString(1, locale.toLanguageTag());
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				CategoryDTO c = loadNext(conn, rs, locale);
				results.put(c.getId(), c);
				putChildren(results, c);
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private CategoryDTO loadNext(Connection conn, ResultSet rs, Locale locale) 
			throws SQLException, DataException {

		CategoryDTO c = new CategoryDTO();

		int i = 1;
		c.setId(rs.getShort(i++));
		c.setName(rs.getString(i++));
		findChildren(conn, c, locale);
		return c;
	}
	
	private void findChildren(Connection conn, CategoryDTO c, Locale locale) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(FIND_CHILDREN_QUERY);
			
			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setShort(i++, c.getId());

			rs = stmt.executeQuery();
			while (rs.next()) {
				CategoryDTO child = loadNext(conn, rs, locale);
				child.setParentId(c.getId());
				c.getChildren().add(child);
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	private void putChildren(Map<Short, CategoryDTO> map, CategoryDTO c) {
		
		if (c.getChildren().isEmpty()) { // No more child categories to add, recursive method ends
			return;
		}
		
		for (CategoryDTO child : c.getChildren()) {
			map.put(child.getId(), child);
			putChildren(map, child);
		}
	}

}
