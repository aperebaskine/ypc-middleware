package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.IDType;

public interface DocumentTypeDAO {
	
	public Map<String, IDType> findAll(Connection conn)
			throws DataException;

}
