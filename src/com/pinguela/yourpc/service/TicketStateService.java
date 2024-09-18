package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;

public interface TicketStateService {
	
	public Map<String, EntityState<Ticket>> findAll()
			throws ServiceException, DataException;

}
