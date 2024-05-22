package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.model.Ticket;

public interface TicketStateService {
	
	public Map<String, ItemState<Ticket>> findAll()
			throws ServiceException, DataException;

}
