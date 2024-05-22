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
import com.pinguela.yourpc.dao.OrderStateDAO;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.util.JDBCUtils;

public class OrderStateDAOImpl 
implements OrderStateDAO {
	
	private static Logger logger = LogManager.getLogger(OrderStateDAOImpl.class);
	
	private static final String QUERY =
			" SELECT os.ID, os.NAME"
			+ " FROM ORDER_STATE os";
	
	@Override
	public Map<String, ItemState<CustomerOrder>> findAll(Connection conn) throws DataException {
		
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
	
	private Map<String, ItemState<CustomerOrder>> loadResults(ResultSet rs) throws SQLException {
		Map<String, ItemState<CustomerOrder>> results = new HashMap<>();
		
		while (rs.next()) {
			ItemState<CustomerOrder> next = loadNext(rs);
			results.put(next.getId(), next);
		}
		
		return results;
	}
	
	private ItemState<CustomerOrder> loadNext(ResultSet rs) throws SQLException {
		ItemState<CustomerOrder> state = new ItemState<>();
		
		int i = 1;
		state.setId(rs.getString(i++));
		state.setName(rs.getString(i++));
		
		return state;
	}

}
