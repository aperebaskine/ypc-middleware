package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CountryDAO;
import com.pinguela.yourpc.model.Country;
import com.pinguela.yourpc.util.JDBCUtils;

public class CountryDAOImpl implements CountryDAO {

	private static final String FINDALL_QUERY = 
			" SELECT c.ID, c.NAME"
			+ " FROM COUNTRY c"
			+ " ORDER BY c.NAME";
	
	private static final Logger logger = LogManager.getLogger(CountryDAOImpl.class);
	
	@Override
	public List<Country> findAll(Connection conn) 
		throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Country> results = new ArrayList<Country>();
		
		try {
			stmt = conn.prepareStatement(FINDALL_QUERY);
			
			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;
			
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	private Country loadNext(ResultSet rs) 
			throws SQLException {
		
		Country country = new Country();
		int i = 1;
		
		country.setId(rs.getString(i++));
		country.setName(rs.getString(i++));
		return country;
	}

}
