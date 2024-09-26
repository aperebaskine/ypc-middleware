package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.model.TicketState;
import com.pinguela.yourpc.model.TicketType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class TicketDAOImpl
extends AbstractMutableDAO<Long, Ticket>
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
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Ticket> root, AbstractCriteria<Ticket> criteria) {
	    TicketCriteria ticketCriteria = (TicketCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (ticketCriteria.getCustomerId() != null) {
	        predicates.add(builder.equal(root.get("customer"), ticketCriteria.getCustomerId()));
	    }
	    if (ticketCriteria.getCustomerEmail() != null) {
	        Join<Ticket, Customer> customerJoin = root.join("customer", JoinType.INNER);
	        predicates.add(builder.equal(customerJoin.get("email"), ticketCriteria.getCustomerEmail()));
	    }
	    if (ticketCriteria.getMinDate() != null) {
	        predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMinDate()));
	    }
	    if (ticketCriteria.getMaxDate() != null) {
	        predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), ticketCriteria.getMaxDate()));
	    }
	    if (ticketCriteria.getState() != null) {
	        Join<Ticket, TicketState> ticketStateJoin = root.join("state", JoinType.INNER);
	        predicates.add(builder.equal(ticketStateJoin.get("id"), ticketCriteria.getState()));
	    }
	    if (ticketCriteria.getType() != null) {
	        Join<Ticket, TicketType> ticketTypeJoin = root.join("type", JoinType.INNER);
	        predicates.add(builder.equal(ticketTypeJoin.get("id"), ticketCriteria.getType()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Ticket> query, Root<Ticket> root,
			AbstractCriteria<Ticket> criteria) {
		// Unused	
	}

	@Override
	public Long create(Session session, Ticket ticket) 
			throws DataException {
		return super.createEntity(session, ticket);
	}
	
	@Override
	public Boolean update(Session session, Ticket ticket) throws DataException {
		return super.updateEntity(session, ticket);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Ticket> updateQuery, Root<Ticket> root,
			AbstractUpdateValues<Ticket> updateValues) {
		// Unused	
	}

}
