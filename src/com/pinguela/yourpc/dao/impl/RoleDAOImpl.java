package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.RoleDAO;
import com.pinguela.yourpc.model.Role;
import com.pinguela.yourpc.util.JDBCUtils;

public class RoleDAOImpl 
implements RoleDAO {

	private static Logger logger = LogManager.getLogger(RoleDAOImpl.class);

	private static final String QUERY =
			" SELECT r.ID, r.NAME"
					+ " FROM ROLE r";

	@Override
	public Map<String, Role> findAll(Connection conn) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(QUERY);

			rs = stmt.executeQuery();
			return loadResults(rs);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private Map<String, Role> loadResults(ResultSet rs) throws SQLException {
		Map<String, Role> results = new HashMap<>();

		while (rs.next()) {
			Role next = loadNext(rs);
			results.put(next.getId(), next);
		}

		return results;
	}

	private Role loadNext(ResultSet rs) throws SQLException {
		Role role = new Role();

		int i = 1;
		role.setId(rs.getString(i++));
		role.setName(rs.getString(i++));

		return role;
	}

}
