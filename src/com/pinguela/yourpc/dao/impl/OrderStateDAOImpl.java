package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.OrderStateDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.OrderState;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class OrderStateDAOImpl 
extends AbstractDAO<String, OrderState>
implements OrderStateDAO {
	
	public OrderStateDAOImpl() {
	}
	
	@Override
	public Map<String, OrderState> findAll(Session session) throws DataException {
		List<OrderState> orderStates = super.findBy(session, null);
		
		Map<String, OrderState> orderStatesById = new LinkedHashMap<String, OrderState>();
		for (OrderState orderState : orderStates) {
			orderStatesById.put(orderState.getId(), orderState);
		}
		return orderStatesById;
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<OrderState> root,
			AbstractCriteria<OrderState> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<OrderState> query, Root<OrderState> root,
			AbstractCriteria<OrderState> criteria) {
		// Unused	
	}
	
}
