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
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.JDBCUtils;

public class CategoryDAOImpl implements CategoryDAO {
	
	private static final String ID_COLUMN = "ID";
	private static final String NAME_COLUMN = "NAME";
	private static final String PARENT_ID_COLUMN = "PARENT_ID";

	private static final String SELECT_COLUMNS = " SELECT"
			+ " c." +ID_COLUMN +","
			+ " c." +NAME_COLUMN +","
			+ " c." +PARENT_ID_COLUMN;
	
	private static final String FROM_TABLE = " FROM CATEGORY c";
	private static final String WHERE_PARENT_ID = " WHERE c.PARENT_ID = ?";
	private static final String WHERE_PARENT_ID_IS_NULL = " WHERE c.PARENT_ID IS NULL";

	private static final String FIND_ROOT_CATEGORIES_QUERY = 
			SELECT_COLUMNS +FROM_TABLE +WHERE_PARENT_ID_IS_NULL;
	private static final String FIND_CHILDREN_QUERY = 
			SELECT_COLUMNS +FROM_TABLE +WHERE_PARENT_ID;

	private static Logger logger = LogManager.getLogger(CategoryDAOImpl.class);

	@Override
	public Map<Short, CategoryDTO> findAll(Connection conn, Locale locale) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<Short, CategoryDTO> results = new TreeMap<Short, CategoryDTO>();

		try {
			stmt = conn.prepareStatement(FIND_ROOT_CATEGORIES_QUERY);

			rs = stmt.executeQuery();
			while (rs.next()) {
				CategoryDTO c = loadNext(conn, rs);
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

	private void findChildren(Connection conn, CategoryDTO c) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(FIND_CHILDREN_QUERY);
			stmt.setShort(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, c.getId());

			rs = stmt.executeQuery();
			while (rs.next()) {
				CategoryDTO child = loadNext(conn, rs);
				child.setParentId(c.getId());
				c.getChildrenIds().add(child.getId());
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private CategoryDTO loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		CategoryDTO c = new CategoryDTO();

		c.setId(rs.getShort(ID_COLUMN));
		c.setName(rs.getString(NAME_COLUMN));
		findChildren(conn, c);
		return c;
	}
	
	private void putChildren(Map<Short, CategoryDTO> map, CategoryDTO c) {
		
		if (c.getChildrenIds().isEmpty()) { // No more child categories to add, recursive method ends
			return;
		}
		
		map.putAll(CategoryUtils.CATEGORIES.get);
		for (CategoryDTO child : c.getChildren().values()) {
			putChildren(map, child);
		}
	}

}
