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
import com.pinguela.yourpc.dao.RMAStateDAO;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.util.JDBCUtils;

public class RMAStateDAOImpl 
implements RMAStateDAO {

	private static Logger logger = LogManager.getLogger(RMAStateDAOImpl.class);

	private static final String QUERY =
			" SELECT rmas.ID, rmas.NAME"
					+ " FROM RMA_STATE rmas";

	@Override
	public Map<String, EntityState<RMA>> findAll(Connection conn) throws DataException {

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

	private Map<String, EntityState<RMA>> loadResults(ResultSet rs) throws SQLException {
		Map<String, EntityState<RMA>> results = new HashMap<>();

		while (rs.next()) {
			EntityState<RMA> next = loadNext(rs);
			results.put(next.getId(), next);
		}

		return results;
	}

	private EntityState<RMA> loadNext(ResultSet rs) throws SQLException {
		EntityState<RMA> state = new EntityState<>();

		int i = 1;
		state.setId(rs.getString(i++));
		state.setName(rs.getString(i++));

		return state;
	}

}