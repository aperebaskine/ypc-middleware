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
import com.pinguela.yourpc.dao.TicketTypeDAO;
import com.pinguela.yourpc.model.EntityType;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketTypeDAOImpl 
implements TicketTypeDAO {

	private static Logger logger = LogManager.getLogger(TicketTypeDAOImpl.class);

	private static final String QUERY =
			" SELECT tt.ID, tt.NAME"
					+ " FROM TICKET_TYPE tt";

	@Override
	public Map<String, EntityType<Ticket>> findAll(Connection conn) throws DataException {

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

	private Map<String, EntityType<Ticket>> loadResults(ResultSet rs) throws SQLException {
		Map<String, EntityType<Ticket>> results = new HashMap<>();

		while (rs.next()) {
			EntityType<Ticket> next = loadNext(rs);
			results.put(next.getId(), next);
		}

		return results;
	}

	private EntityType<Ticket> loadNext(ResultSet rs) throws SQLException {
		EntityType<Ticket> type = new EntityType<>();

		int i = 1;
		type.setId(rs.getString(i++));
		type.setName(rs.getString(i++));

		return type;
	}

}