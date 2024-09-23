package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Results;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public abstract class AbstractDAO<T> {

	private static final int BATCH_SIZE = 50;

	private static Logger logger = LogManager.getLogger(AbstractDAO.class);

	private Class<T> targetClass;

	protected AbstractDAO(Class<T> targetClass) {
		this.targetClass = targetClass;
	}

	protected Object persist(Session session, T entity) 
			throws DataException {
		session.persist(entity);
		session.flush();
		return session.getIdentifier(entity);
	}

	protected List<Object> persist(Session session, List<T> entities)
			throws DataException {
		try {
			for (int i = 0; i<entities.size(); i++) {
				session.persist(entities.get(i));
				processBatchIfFull(session, entities.size(), i);
			}

			List<Object> identifiers = new ArrayList<Object>();
			for (T entity : entities) {
				identifiers.add(session.getIdentifier(entity));
			}
			return identifiers;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}	
	}

	protected void merge(Session session, T entity) 
			throws DataException {
		merge(session, Arrays.asList(entity));
	}

	protected void merge(Session session, List<T> entities)
			throws DataException {
		try {
			for (int i = 0; i<entities.size(); i++) {
				session.merge(entities.get(i));
				processBatchIfFull(session, entities.size(), i);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected void remove(Session session, T entity)
			throws DataException {
		remove(session, Arrays.asList(entity));
	}

	protected void remove(Session session, List<T> entities) 
			throws DataException {
		try {
			for (int i = 0; i<entities.size(); i++) {
				session.remove(entities.get(i));
				processBatchIfFull(session, entities.size(), i);
			}
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	private static void processBatchIfFull(Session session, int entityCount, int current) {
		if ((current > 0 && current % BATCH_SIZE == 0) || current == entityCount-1) {
			session.flush();
			session.clear();
		}
	}

	protected T findById(Session session, Object id) 
			throws DataException {
		try {
			return session.find(targetClass, id);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected List<T> findBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildFindByQuery(session, criteria);
			return session.createQuery(query).getResultList();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected Results<T> findBy(Session session, AbstractCriteria<T> criteria, int pos, int pageSize) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildFindByQuery(session, criteria);
			Results<T> results = new Results<T>();
			
			ScrollableResults<T> scrollableResults = session.createQuery(query).scroll();
			results.setResultCount(getResultCount(scrollableResults));
			results.setPage(getPage(scrollableResults, pos, pageSize));

			return results;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	private List<T> getPage(ScrollableResults<T> results, int pos, int pageSize) {
		List<T> page = new ArrayList<>();
		results.position(pos);
		for (int i = 0; i < pageSize && results.next(); i++) {
			page.add(results.get());
		}
		return page;
	}

	private int getResultCount(ScrollableResults<T> results) {
		results.last();
		return results.getRowNumber() +1;
	}

	private CriteriaQuery<T> buildFindByQuery(Session session, AbstractCriteria<T> criteria) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(targetClass);
		Root<T> root = query.from(targetClass);

		setFindByCriteria(builder, query, root, criteria);

		Path<T> orderBy = root.get(criteria.getOrderBy());
		query.orderBy(criteria.getAscDesc() == AbstractCriteria.ASC ? 
				builder.asc(orderBy) : builder.desc(orderBy));

		return query;
	}

	protected Class<T> getTargetClass() {
		return targetClass;
	}

	protected abstract void setFindByCriteria(CriteriaBuilder builder,
			CriteriaQuery<T> query, Root<T> root, AbstractCriteria<T> criteria);

}
