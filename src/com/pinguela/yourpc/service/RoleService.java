package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Role;

public interface RoleService {
	
	public Map<String, Role> findAll()
			throws ServiceException, DataException;

}
