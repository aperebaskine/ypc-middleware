package com.pinguela.yourpc.dao.impl.builder;

import org.hibernate.Session;

import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.dto.AbstractDTO;

public abstract class AbstractQueryBuilder<Q, E extends AbstractEntity<?>, D extends AbstractDTO<E>, C extends AbstractCriteria<E>> {
	
	private Class<D> dtoClass;
	private Class<E> entityClass;
	
	protected AbstractQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		this.dtoClass = dtoClass;
		this.entityClass = entityClass; 
	}
	
	protected Class<D> getDtoClass() {
		return dtoClass;
	}
	
	protected Class<E> getEntityClass() {
		return entityClass;
	}
	
	protected abstract Q buildQuery(Session session, C criteria);
	
	protected abstract void setParameters(Q query, C criteria);
	
	public abstract Object executeQuery(Session session, C criteria);
	
}


