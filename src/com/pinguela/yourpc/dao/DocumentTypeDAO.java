package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.DocumentType;

public interface DocumentTypeDAO {
	
	public Map<String, DocumentType> findAll(Connection conn)
			throws DataException;

}
