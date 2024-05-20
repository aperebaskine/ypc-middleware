package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Province;

public interface ProvinceDAO {
	
	public List<Province> findByCountry(Connection conn, String countryId)
			throws DataException;

}
