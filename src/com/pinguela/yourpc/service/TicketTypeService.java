package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.TicketType;

public interface TicketTypeService {
	
	public Map<String, TicketType> findAll()
			throws ServiceException, DataException;

}
