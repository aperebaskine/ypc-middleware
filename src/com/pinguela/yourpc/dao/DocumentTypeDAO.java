package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.IDType;

public interface DocumentTypeDAO {
	
	public Map<String, IDType> findAll(Session session)
			throws DataException;

}
