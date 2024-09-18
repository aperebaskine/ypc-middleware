package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.EntityType;
import com.pinguela.yourpc.model.Ticket;

public interface TicketTypeService {
	
	public Map<String, EntityType<Ticket>> findAll()
			throws ServiceException, DataException;

}
