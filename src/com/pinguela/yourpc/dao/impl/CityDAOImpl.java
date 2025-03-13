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
import com.pinguela.yourpc.dao.CityDAO;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.util.JDBCUtils;

public class CityDAOImpl implements CityDAO {

	private static final String SELECT_COLUMNS = " SELECT ci.ID, ci.NAME, ci.PROVINCE_ID";
	private static final String FROM_TABLE = " FROM CITY ci";
	private static final String WHERE_PROVINCE_ID = " WHERE ci.PROVINCE_ID = ?";
	private static final String ORDER_BY = " ORDER BY ci.NAME";
	
	private static final String FIND_BY_PROVINCE_QUERY = SELECT_COLUMNS +FROM_TABLE +WHERE_PROVINCE_ID +ORDER_BY;
	
	private static final Logger logger = LogManager.getLogger(CityDAOImpl.class);

	@Override
	public List<City> findByProvince(Connection conn, Integer provinceId)
		throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<City> results = new ArrayList<City>();

		try {
			stmt = conn.prepareStatement(FIND_BY_PROVINCE_QUERY);

			int index = 1;
			stmt.setInt(index++, provinceId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;

		} catch (SQLException sqle) {
			logger.trace(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private City loadNext(ResultSet rs)
			throws SQLException {

		City city = new City();
		int i = 1;

		city.setId(rs.getInt(i++));
		city.setName(rs.getString(i++));
		city.setProvinceId(rs.getInt(i++));
		return city;
	}

}
