package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Country;

public interface CountryDAO {
	
	public List<Country> findAll(Connection conn)
			throws DataException;

}
