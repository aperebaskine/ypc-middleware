package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ItemType;
import com.pinguela.yourpc.model.Ticket;

public interface TicketTypeService {
	
	public Map<String, ItemType<Ticket>> findAll()
			throws ServiceException, DataException;

}
