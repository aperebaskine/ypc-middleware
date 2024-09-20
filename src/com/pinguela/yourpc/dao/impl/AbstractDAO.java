package com.pinguela.yourpc.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.Results;

import jakarta.persistence.criteria.CriteriaQuery;

public abstract class AbstractDAO<PK, T extends AbstractEntity<PK>> {
	
	private Class<T> targetClass;
	
	protected AbstractDAO(Class<T> targetClass) {
		this.targetClass = targetClass;
	}
	
	protected T findById(Session session, PK id) {
		return session.get(targetClass, id);
	}
	
	protected List<T> findBy(Session session, AbstractEntityCriteria<PK, T> criteria, Query<T> q) {
		return null;
	}
	
	protected Results<T> findBy(Session session, AbstractEntityCriteria<PK, T> criteria, CriteriaQuery<T> q, int pos, int pageSize) {
		return null;
	}
	
	protected void create(T t, Session session) {
		session.persist(t);
	}
	
	protected void update(T t, Session session) {
		session.merge(t);
	}
	
	protected void delete(T t, Session session) {
		session.remove(t);
	}

}
