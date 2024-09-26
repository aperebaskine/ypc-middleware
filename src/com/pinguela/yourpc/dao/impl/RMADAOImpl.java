package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.model.RMAState;
import com.pinguela.yourpc.model.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class RMADAOImpl 
extends AbstractMutableDAO<Long, RMA>
implements RMADAO {

	public RMADAOImpl() {
	}

	@Override
	public RMA findById(Session session, Long rmaId) 
			throws DataException {
		return super.findById(session, rmaId);
	}

	@Override
	public List<RMA> findBy(Session session, RMACriteria criteria) throws DataException {
		return super.findBy(session, criteria);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<RMA> root, AbstractCriteria<RMA> criteria) {
	    RMACriteria rmaCriteria = (RMACriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    Join<RMA, OrderLine> orderLineJoin = null;

	    if (rmaCriteria.getCustomerId() != null) {
	        predicates.add(builder.equal(root.get("customer").get("id"), rmaCriteria.getCustomerId()));
	    }
	    if (rmaCriteria.getCustomerEmail() != null) {
	        Join<RMA, Customer> joinCustomer = root.join("customer");
	        joinCustomer.on(builder.equal(joinCustomer.get("email"), rmaCriteria.getCustomerEmail()));
	    }
	    if (rmaCriteria.getState() != null) {
	        Join<RMA, RMAState> joinState = root.join("state");
	        predicates.add(builder.equal(joinState.get("id"), rmaCriteria.getState()));
	    }
	    if (rmaCriteria.getOrderId() != null) {
	        orderLineJoin = root.join("orderLines");
	        orderLineJoin.on(builder.equal(orderLineJoin.get("customerOrder").get("id"), rmaCriteria.getOrderId()));
	    }
	    if (rmaCriteria.getTicketId() != null) {
	        if (orderLineJoin == null) {
	            orderLineJoin = root.join("orderLines");
	        }
	        Join<OrderLine, Ticket> joinTicket = orderLineJoin.join("ticket");
	        joinTicket.on(builder.equal(joinTicket.get("id"), rmaCriteria.getTicketId()));
	    }
	    if (rmaCriteria.getMinDate() != null) {
	        predicates.add(builder.greaterThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMinDate()));
	    }
	    if (rmaCriteria.getMaxDate() != null) {
	        predicates.add(builder.lessThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMaxDate()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<RMA> query, Root<RMA> root,
			AbstractCriteria<RMA> criteria) {
	    RMACriteria rmaCriteria = (RMACriteria) criteria;

	    if (rmaCriteria.getTicketId() != null
	    		|| rmaCriteria.getOrderId() != null) {
	    	query.groupBy(root.get("id"));
	    }
	}

	@Override
	public Long create(Session session, RMA rma) throws DataException {
		return super.createEntity(session, rma);
	}

	@Override
	public Boolean update(Session session, RMA rma) throws DataException {
		return super.updateEntity(session, rma);
	}

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<RMA> updateQuery, Root<RMA> root,
			AbstractUpdateValues<RMA> updateValues) {
		// Unused
	}

}
