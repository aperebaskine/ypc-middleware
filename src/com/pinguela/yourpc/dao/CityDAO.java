package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.City;

public interface CityDAO {
	
	public List<City> findByProvince(Connection conn, Integer provinceId)
			throws DataException;

}
