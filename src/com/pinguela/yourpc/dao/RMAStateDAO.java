package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.model.RMA;

public interface RMAStateDAO {
	
	public Map<String, ItemState<RMA>> findAll(Connection conn)
			throws DataException;

}
