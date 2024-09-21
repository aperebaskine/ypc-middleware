package com.pinguela.yourpc.dao;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;

public interface TicketDAO {
	
	public Ticket findById(Session session, Long ticketId)
			throws DataException;
	
	public Results<Ticket> findBy(Session session, TicketCriteria criteria, int pos, int pageSize)
			throws DataException;
	
	public Long create(Session session, Ticket ticket)
			throws DataException;
	
	public Boolean update(Session session, Ticket ticket)
			throws DataException;

}
