package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketTypeDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.TicketType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketTypeDAOImpl 
extends AbstractDAO<String, TicketType>
implements TicketTypeDAO {

	public TicketTypeDAOImpl() {
	}
	
	@Override
	public Map<String, TicketType> findAll(Session session) throws DataException {
		List<TicketType> ticketTypes = super.findBy(session, null);
		return mapByPrimaryKey(ticketTypes);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<TicketType> root,
			AbstractCriteria<TicketType> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<TicketType> query, Root<TicketType> root,
			AbstractCriteria<TicketType> criteria) {
		// Unused	
	}

}