package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.Category;
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
	public Map<Short, Category> findAll(Connection conn) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Map<Short, Category> results = new TreeMap<Short, Category>();

		try {
			stmt = conn.prepareStatement(FIND_ROOT_CATEGORIES_QUERY);

			rs = stmt.executeQuery();
			while (rs.next()) {
				Category c = loadNext(conn, rs);
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

	private void findChildren(Connection conn, Category c) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(FIND_CHILDREN_QUERY);
			stmt.setShort(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, c.getId());

			rs = stmt.executeQuery();
			while (rs.next()) {
				Category child = loadNext(conn, rs);
				child.setParent(c);
				c.getChildren().put(child.getId(), child);
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private Category loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		Category c = new Category();

		c.setId(rs.getShort(ID_COLUMN));
		c.setName(rs.getString(NAME_COLUMN));
		findChildren(conn, c);
		return c;
	}
	
	private void putChildren(Map<Short, Category> map, Category c) {
		
		if (c.getChildren().isEmpty()) { // No more child categories to add, recursive method ends
			return;
		}
		
		map.putAll(c.getChildren());
		for (Category child : c.getChildren().values()) {
			putChildren(map, child);
		}
	}

}
