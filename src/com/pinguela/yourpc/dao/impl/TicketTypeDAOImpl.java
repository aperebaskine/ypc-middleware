package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketTypeDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.TicketType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class TicketTypeDAOImpl 
extends AbstractDAO<TicketType>
implements TicketTypeDAO {

	public TicketTypeDAOImpl() {
		super(TicketType.class);
	}
	
	@Override
	public Map<String, TicketType> findAll(Session session) throws DataException {
		List<TicketType> ticketTypes = super.findBy(session, null);
		
		Map<String, TicketType> ticketTypesById = new LinkedHashMap<String, TicketType>();
		for (TicketType ticketType : ticketTypes) {
			ticketTypesById.put(ticketType.getId(), ticketType);
		}
		return ticketTypesById;
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<TicketType> query, Root<TicketType> root,
			AbstractCriteria<TicketType> criteria) {}

}