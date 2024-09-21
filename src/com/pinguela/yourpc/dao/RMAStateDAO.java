package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.RMA;

public interface RMAStateDAO {
	
	public Map<String, EntityState<RMA>> findAll(Session session)
			throws DataException;

}
