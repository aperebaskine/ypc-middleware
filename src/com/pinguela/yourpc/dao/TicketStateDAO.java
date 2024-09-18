package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;

public interface TicketStateDAO {
	
	public Map<String, EntityState<Ticket>> findAll(Connection conn)
			throws DataException;

}
