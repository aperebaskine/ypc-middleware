package com.pinguela.yourpc.service;

import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.TicketState;

public interface TicketStateService {
	
	public Map<String, TicketState> findAll()
			throws ServiceException, DataException;

}
