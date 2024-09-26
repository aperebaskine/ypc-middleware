package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.SimpleCriteria;
import com.pinguela.yourpc.model.TicketMessage;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketMessageDAOImpl 
extends AbstractMutableDAO<Long, TicketMessage>
implements TicketMessageDAO {	
	
	public TicketMessageDAOImpl() {
	}

	@Override
	public List<TicketMessage> findByTicket(Session session, Long ticketId) 
			throws DataException {
			SimpleCriteria<TicketMessage> criteria = new SimpleCriteria<TicketMessage>(ticketId);
			return super.findBy(session, criteria);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder,
			Root<TicketMessage> root, AbstractCriteria<TicketMessage> criteria) {
	    SimpleCriteria<TicketMessage> simpleCriteria = (SimpleCriteria<TicketMessage>) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (simpleCriteria.getValue() != null) {
	        predicates.add(builder.equal(root.get("ticket").get("id"), simpleCriteria.getValue()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<TicketMessage> query,
			Root<TicketMessage> root, AbstractCriteria<TicketMessage> criteria) {
		// Unused	
	}

	@Override
	public Long create(Session session, TicketMessage ticketMessage)
			throws DataException {
		return super.createEntity(session, ticketMessage);
	}

	@Override
	public Boolean delete(Session session, Long messageId) throws DataException {
		return super.deleteEntity(session, messageId);
	}

	@Override
	public Boolean deleteByTicket(Session session, Long ticketId) throws DataException {
		SimpleCriteria<TicketMessage> criteria = new SimpleCriteria<TicketMessage>(ticketId);
		return super.deleteBy(session, criteria);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<TicketMessage> updateQuery,
			Root<TicketMessage> root, AbstractUpdateValues<TicketMessage> updateValues) {
		// Unused	
	}
	
}
