package com.pinguela.yourpc.dao.impl;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.model.RMAState;
import com.pinguela.yourpc.model.Ticket;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class RMADAOImpl 
extends AbstractDAO<Long, RMA>
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
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<RMA> query, Root<RMA> root,
			AbstractCriteria<RMA> criteria) {

		RMACriteria rmaCriteria = (RMACriteria) criteria;

		Join<RMA, OrderLine> orderLineJoin = null;
		boolean groupBy = false;

		if (rmaCriteria.getCustomerId() != null) {
			query.where(builder.equal(root.get("customer").get("id"), rmaCriteria.getCustomerId()));
		}
		if (rmaCriteria.getCustomerEmail() != null) {
			Join<RMA, Customer> joinCustomer = root.join("customer");
			joinCustomer.on(builder.equal(joinCustomer.get("email"), rmaCriteria.getCustomerEmail()));
		}
		if (rmaCriteria.getState() != null) {
			Join<RMA, RMAState> joinState = root.join("state");
			query.where(builder.equal(joinState.get("id"), rmaCriteria.getState()));
		}
		if (rmaCriteria.getOrderId() != null) {
			groupBy = true;
			orderLineJoin = root.join("orderLines");
			orderLineJoin.on(builder.equal(
					orderLineJoin.get("customerOrder").get("id"), rmaCriteria.getOrderId()));
		}
		if (rmaCriteria.getTicketId() != null) {
			groupBy = true;
			if (orderLineJoin == null) {
				orderLineJoin = root.join("orderLines");
			}
			Join<OrderLine, Ticket> joinTicket = orderLineJoin.join("ticket");
			joinTicket.on(builder.equal(joinTicket.get("id"), rmaCriteria.getTicketId()));
		}
		if (rmaCriteria.getMinDate() != null) {
			query.where(builder.greaterThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMinDate()));
		}
		if (rmaCriteria.getMaxDate() != null) {
			query.where(builder.lessThanOrEqualTo(root.get("creationDate"), rmaCriteria.getMaxDate()));
		}

		if (groupBy) {
			query.groupBy(root.get("id"));
		}
	}

	@Override
	public Long create(Session session, RMA rma) throws DataException {
		return super.persist(session, rma);
	}

	@Override
	public Boolean update(Session session, RMA rma) throws DataException {
		return super.merge(session, rma);
	}

}
