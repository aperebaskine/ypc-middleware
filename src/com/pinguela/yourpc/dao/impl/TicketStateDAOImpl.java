package com.pinguela.yourpc.dao.impl;

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
		return mapByPrimaryKey(ticketStates);
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