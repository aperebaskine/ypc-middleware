package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ItemState;
import com.pinguela.yourpc.model.Ticket;

public interface TicketStateDAO {
	
	public Map<String, ItemState<Ticket>> findAll(Connection conn)
			throws DataException;

}
