package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.OrderLineDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.OrderLineCriteria;
import com.pinguela.yourpc.model.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrderLineDAOImpl 
extends AbstractMutableDAO<Long, OrderLine>
implements OrderLineDAO {

	public OrderLineDAOImpl() {
	}

	@Override
	public List<OrderLine> findByCustomerOrder(Session session, long orderId) 
			throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setOrderId(orderId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<OrderLine> findByTicket(Session session, long ticketId) throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setTicketId(ticketId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<OrderLine> findByRMA(Session session, long rmaId) throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setRmaId(rmaId);
		return super.findBy(session, criteria);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<OrderLine> root, AbstractCriteria<OrderLine> criteria) {
	    OrderLineCriteria orderLineCriteria = (OrderLineCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (orderLineCriteria.getOrderId() != null) {
	        predicates.add(builder.equal(root.get("order").get("id"), orderLineCriteria.getOrderId()));
	    }

	    if (orderLineCriteria.getTicketId() != null) {
	        Join<OrderLine, Ticket> joinTicket = root.join("ticket");
	        joinTicket.on(builder.equal(joinTicket.get("ticketId"), orderLineCriteria.getTicketId()));
	    }

	    if (orderLineCriteria.getRmaId() != null) {
	        Join<OrderLine, Ticket> joinRma = root.join("rma");
	        joinRma.on(builder.equal(joinRma.get("rmaId"), orderLineCriteria.getRmaId()));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<OrderLine> query, Root<OrderLine> root,
			AbstractCriteria<OrderLine> criteria) {
		// Unused	
	}

	@Override
	public Boolean create(Session session, List<OrderLine> olList) 
			throws DataException {
		List<Long> identifiers = super.createBatch(session, olList);
		return identifiers.size() == olList.size();
	}

	@Override
	public Boolean deleteByCustomerOrder(Session session, long orderId) 
			throws DataException {
		OrderLineCriteria criteria = new OrderLineCriteria();
		criteria.setOrderId(orderId);
		return super.deleteBy(session, criteria);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<OrderLine> updateQuery, Root<OrderLine> root,
			AbstractUpdateValues<OrderLine> updateValues) {
		// Unused	
	}

}
