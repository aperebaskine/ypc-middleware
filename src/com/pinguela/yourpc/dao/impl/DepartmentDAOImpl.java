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
import com.pinguela.yourpc.dao.DepartmentDAO;
import com.pinguela.yourpc.model.Department;
import com.pinguela.yourpc.util.JDBCUtils;

public class DepartmentDAOImpl 
implements DepartmentDAO {

	private static Logger logger = LogManager.getLogger(DepartmentDAOImpl.class);

	private static final String QUERY =
			" SELECT d.ID, d.NAME"
					+ " FROM DEPARTMENT d";

	@Override
	public Map<String, Department> findAll(Connection conn) throws DataException {

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

	private Map<String, Department> loadResults(ResultSet rs) throws SQLException {
		Map<String, Department> results = new HashMap<>();

		while (rs.next()) {
			Department next = loadNext(rs);
			results.put(next.getId(), next);
		}

		return results;
	}

	private Department loadNext(ResultSet rs) throws SQLException {
		Department department = new Department();

		int i = 1;
		department.setId(rs.getString(i++));
		department.setName(rs.getString(i++));

		return department;
	}

}
