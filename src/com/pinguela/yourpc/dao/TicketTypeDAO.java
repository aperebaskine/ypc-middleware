package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityType;
import com.pinguela.yourpc.model.Ticket;

public interface TicketTypeDAO {
	
	public Map<String, EntityType<Ticket>> findAll(Connection conn)
			throws DataException;

}
