package com.pinguela.yourpc.service;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.TicketMessage;

public interface TicketMessageService {
	
	public Long create(TicketMessage ticketMessage)
			throws ServiceException, DataException;
	
	public Boolean delete(Long messageId)
			throws ServiceException, DataException;

}
