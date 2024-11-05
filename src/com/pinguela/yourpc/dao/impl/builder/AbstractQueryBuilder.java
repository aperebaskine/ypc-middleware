package com.pinguela.yourpc.dao.impl.builder;

import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;

public abstract class AbstractQueryBuilder<PK extends Comparable<PK>,
		E extends AbstractEntity<PK>,
		D extends AbstractDTO<PK, E>,
		C extends AbstractEntityCriteria<PK, E>> {

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

	public Query<D> buildQuery(Session session, PK id) {
		return buildQuery(session, id, null, null);
	}
	
	public Query<D> buildQuery(Session session, C criteria) {
		return buildQuery(session, null, null, criteria);
	}

	public Query<D> buildQuery(Session session, Locale locale) {
		return buildQuery(session, null, locale, null);
	}

	public Query<D> buildQuery(Session session, PK id, Locale locale) {
		return buildQuery(session, id, locale, null);
	}

	public abstract Query<D> buildQuery(Session session, PK id, Locale locale, C criteria);

	public Query<D> buildQuery(Session session, C criteria, int pos, int pageSize) {
		return buildQuery(session, null, null, criteria)
				.setFirstResult(pos)
				.setMaxResults(pageSize);
	}

}
