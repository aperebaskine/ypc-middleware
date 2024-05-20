package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.TicketMessage;

public interface TicketMessageDAO {
	
	public List<TicketMessage> findByTicket(Connection conn, Long ticketId)
			throws DataException;
	
	public Long create(Connection conn, TicketMessage ticketMessage)
			throws DataException;
	
	public Boolean delete(Connection conn, Long messageId)
			throws DataException;
	
	public Boolean deleteByTicket(Connection conn, Long ticketId)
			throws DataException;

}
