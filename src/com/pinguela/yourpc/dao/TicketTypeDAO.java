package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ItemType;
import com.pinguela.yourpc.model.Ticket;

public interface TicketTypeDAO {
	
	public Map<String, ItemType<Ticket>> findAll(Connection conn)
			throws DataException;

}
