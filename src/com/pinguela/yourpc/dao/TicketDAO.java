package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;

public interface TicketDAO {
	
	public Ticket findById(Connection conn, Long ticketId, Locale locale)
			throws DataException;
	
	public Results<Ticket> findBy(Connection conn, TicketCriteria criteria, Locale locale, int pos, int pageSize)
			throws DataException;
	
	public boolean matchesCustomer(Connection conn, Integer ticketId, Integer customerId)
			throws DataException;
	
	public Long create(Connection conn, Ticket ticket)
			throws DataException;
	
	public Boolean update(Connection conn, Ticket ticket)
			throws DataException;

}
