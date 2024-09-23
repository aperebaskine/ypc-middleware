package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.RMAState;

public interface RMAStateDAO {
	
	public Map<String, RMAState> findAll(Session session)
			throws DataException;

}
