package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketStateDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.TicketState;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketStateDAOImpl 
extends AbstractDAO<String, TicketState>
implements TicketStateDAO {
	
	public TicketStateDAOImpl() {
	}
	
	@Override
	public Map<String, TicketState> findAll(Session session) throws DataException {
		List<TicketState> ticketStates = super.findBy(session, null);
		
		Map<String, TicketState> ticketStatesById = new LinkedHashMap<String, TicketState>();
		for (TicketState ticketState : ticketStates) {
			ticketStatesById.put(ticketState.getId(), ticketState);
		}
		return ticketStatesById;
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<TicketState> root,
			AbstractCriteria<TicketState> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<TicketState> query, Root<TicketState> root,
			AbstractCriteria<TicketState> criteria) {
		// Unused	
	}
	
}