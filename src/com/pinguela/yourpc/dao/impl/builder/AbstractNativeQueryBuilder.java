package com.pinguela.yourpc.dao.impl.builder;

import org.hibernate.Session;

import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.dto.AbstractDTO;

public abstract class AbstractNativeQueryBuilder<E extends AbstractEntity<?>, D extends AbstractDTO<E>, C extends AbstractCriteria<E>> 
extends AbstractQueryBuilder<String, E, D, C> {
	
	public AbstractNativeQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		super(dtoClass, entityClass);
	}

	@Override
	public Object executeQuery(Session session, C criteria) {
		return session.createNativeQuery(buildQuery(session, criteria), getDtoClass());
	}

}
