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
import com.pinguela.yourpc.dao.TicketStateDAO;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketStateDAOImpl 
implements TicketStateDAO {

	private static Logger logger = LogManager.getLogger(TicketStateDAOImpl.class);

	private static final String QUERY =
			" SELECT ts.ID, ts.NAME"
					+ " FROM TICKET_STATE ts";

	@Override
	public Map<String, EntityState<Ticket>> findAll(Connection conn) throws DataException {

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

	private Map<String, EntityState<Ticket>> loadResults(ResultSet rs) throws SQLException {
		Map<String, EntityState<Ticket>> results = new HashMap<>();

		while (rs.next()) {
			EntityState<Ticket> next = loadNext(rs);
			results.put(next.getId(), next);
		}

		return results;
	}

	private EntityState<Ticket> loadNext(ResultSet rs) throws SQLException {
		EntityState<Ticket> state = new EntityState<>();

		int i = 1;
		state.setId(rs.getString(i++));
		state.setName(rs.getString(i++));

		return state;
	}

}