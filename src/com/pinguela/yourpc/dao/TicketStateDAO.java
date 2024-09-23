package com.pinguela.yourpc.dao;

import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.TicketState;

public interface TicketStateDAO {
	
	public Map<String, TicketState> findAll(Session session)
			throws DataException;

}
