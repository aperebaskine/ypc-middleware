package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.RMA;

public interface RMAStateDAO {
	
	public Map<String, EntityState<RMA>> findAll(Connection conn)
			throws DataException;

}
