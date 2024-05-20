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
import com.pinguela.yourpc.dao.ProvinceDAO;
import com.pinguela.yourpc.model.Province;
import com.pinguela.yourpc.util.JDBCUtils;

public class ProvinceDAOImpl implements ProvinceDAO {

	private static final String SELECT_COLUMNS =
			" SELECT pr.ID, pr.NAME, pr.COUNTRY_ID";
	private static final String FROM_TABLE =
			" FROM PROVINCE pr";
	private static final String FILTER_CONDITION =
			" WHERE pr.COUNTRY_ID = ?";
	private static final String FINDBYCOUNTRY_QUERY = SELECT_COLUMNS +FROM_TABLE +FILTER_CONDITION;
	
	private static Logger logger = LogManager.getLogger(ProvinceDAOImpl.class);

	@Override
	public List<Province> findByCountry(Connection conn, String countryId) 
		throws DataException {
		
		if (countryId == null) {
			return null;
		}
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Province> results = new ArrayList<Province>();

		try {
			stmt = conn.prepareStatement(FINDBYCOUNTRY_QUERY);

			int index = 1;
			stmt.setString(index++, countryId);

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

	private Province loadNext(ResultSet rs)
			throws SQLException {

		Province pr = new Province();
		int i = 1;
		
		pr.setId(rs.getInt(i++));
		pr.setName(rs.getString(i++));
		pr.setCountryId(rs.getString(i++));
		return pr;
	}

}
