package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.TicketType;

public interface TicketTypeDAO {
	
	public Map<String, TicketType> findAll(Session session)
			throws DataException;

}
