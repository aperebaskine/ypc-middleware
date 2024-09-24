package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.RMAStateDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.RMAState;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class RMAStateDAOImpl
extends AbstractDAO<String, RMAState>
implements RMAStateDAO {
	
	public RMAStateDAOImpl() {
	}
	
	@Override
	public Map<String, RMAState> findAll(Session session) throws DataException {
		List<RMAState> rmaStates = super.findBy(session, null);
		Map<String, RMAState> rmaStatesById = new LinkedHashMap<String, RMAState>();
		
		for (RMAState rmaState : rmaStates) {
			rmaStatesById.put(rmaState.getId(), rmaState);
		}
		
		return rmaStatesById;
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<RMAState> query, Root<RMAState> root,
			AbstractCriteria<RMAState> criteria) {}

}