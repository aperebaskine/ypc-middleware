package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Role;

public interface RoleDAO {
	
	public Map<String, Role> findAll(Connection conn)
			throws DataException;

}
