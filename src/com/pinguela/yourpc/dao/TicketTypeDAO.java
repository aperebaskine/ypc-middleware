package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.EntityType;
import com.pinguela.yourpc.model.Ticket;

public interface TicketTypeDAO {
	
	public Map<String, EntityType<Ticket>> findAll(Session session)
			throws DataException;

}
