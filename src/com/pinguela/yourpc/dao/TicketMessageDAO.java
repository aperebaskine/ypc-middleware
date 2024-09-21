package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.TicketMessage;

public interface TicketMessageDAO {
	
	public List<TicketMessage> findByTicket(Session session, Long ticketId)
			throws DataException;
	
	public Long create(Session session, TicketMessage ticketMessage)
			throws DataException;
	
	public Boolean delete(Session session, Long messageId)
			throws DataException;
	
	public Boolean deleteByTicket(Session session, Long ticketId)
			throws DataException;

}
