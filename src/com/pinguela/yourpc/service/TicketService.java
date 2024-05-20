package com.pinguela.yourpc.service;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;

public interface TicketService {
	
	public Ticket findById(Long ticketId)
			throws ServiceException, DataException;
	
	public Results<Ticket> findBy(TicketCriteria criteria, int pos, int pageSize)
			throws ServiceException, DataException;
	
	public Long create(Ticket ticket)
			throws ServiceException, DataException;
	
	public Boolean update(Ticket ticket)
			throws ServiceException, DataException;

}
