package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;

public interface TicketStateDAO {
	
	public Map<String, EntityState<Ticket>> findAll(Session session)
			throws DataException;

}
