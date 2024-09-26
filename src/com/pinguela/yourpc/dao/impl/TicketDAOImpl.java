package com.pinguela.yourpc.dao.impl;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.model.TicketState;
import com.pinguela.yourpc.model.TicketType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class TicketDAOImpl
extends AbstractDAO<Long, Ticket>
implements TicketDAO {

	public TicketDAOImpl() {
	}

	@Override
	public Ticket findById(Session session, Long ticketId) 
			throws DataException {
		return super.findById(session, ticketId);
	}

	@Override
	public Results<Ticket> findBy(Session session, TicketCriteria ticketCriteria, int pos, int pageSize)
			throws DataException {
		return super.findBy(session, ticketCriteria, pos, pageSize);
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Ticket> query, Root<Ticket> root,
			AbstractCriteria<Ticket> criteria) {
		TicketCriteria ticketCriteria = (TicketCriteria) criteria;
		
		if (ticketCriteria.getCustomerId() != null) {
			query.where(builder.equal(root.get("customer"), ticketCriteria.getCustomerId()));
		}
		if (ticketCriteria.getCustomerEmail() != null) {
			Join<Ticket, Customer> customerJoin = root.join("customer", JoinType.INNER);
			query.where(builder.equal(customerJoin.get("email"), ticketCriteria.getCustomerEmail()));
		}
		if (ticketCriteria.getMinDate() != null) {
			query.where(builder.greaterThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMinDate()));
		}
		if (ticketCriteria.getMaxDate() != null) {
			query.where(builder.lessThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMaxDate()));
		}
		if (ticketCriteria.getState() != null) {
			Join<Ticket, TicketState> ticketStateJoin = root.join("state", JoinType.INNER);
			query.where(builder.equal(ticketStateJoin.get("id"), ticketCriteria.getState()));
		}
		if (ticketCriteria.getType() != null) {
			Join<Ticket, TicketType> ticketTypeJoin = root.join("type", JoinType.INNER);
			query.where(builder.equal(ticketTypeJoin.get("id"), ticketCriteria.getType()));
		}
	}

	@Override
	public Long create(Session session, Ticket ticket) 
			throws DataException {
		return super.persist(session, ticket);
	}
	
	@Override
	public Boolean update(Session session, Ticket ticket) throws DataException {
		return super.merge(session, ticket);
	}

}
