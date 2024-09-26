package com.pinguela.yourpc.dao.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.util.ReflectionUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public abstract class AbstractDAO<PK, T extends AbstractEntity<PK>> {

	private static final int BATCH_SIZE = 50;

	private static Logger logger = LogManager.getLogger(AbstractDAO.class);

	private Class<T> targetClass;

	protected AbstractDAO() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		Type targetClassType = ReflectionUtils.getTypeParameterBounds(this.getClass())[1];
		targetClass = (Class<T>) ReflectionUtils.getClass(targetClassType);
	}

	@SuppressWarnings("unchecked")
	protected PK persist(Session session, T entity) 
			throws DataException {
		try {
			session.persist(entity);
			session.flush();
			return (PK) session.getIdentifier(entity);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected List<PK> batchPersist(Session session, List<T> entities)
			throws DataException {
		try {
			List<PK> identifiers = new ArrayList<>();

			for (int i = 0, batchStartIndex = 0; i<entities.size(); i++) {
				session.persist(entities.get(i));
				batchStartIndex = processBatchIfFull(
						session, entities, identifiers, batchStartIndex, i);
			}

			return identifiers;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}	
	}

	protected boolean merge(Session session, T entity) 
			throws DataException {
		try {
			T persistedEntity = session.find(targetClass, entity.getId());
			if (persistedEntity == null) {
				return false;
			}

			session.merge(entity);
			session.flush();
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean batchMerge(Session session, List<T> entities)
			throws DataException {
		try {
			List<T> persistedEntities = session
					.byMultipleIds(targetClass)
					.multiLoad(getIdentifiers(entities));

			if (persistedEntities.isEmpty() ||
					persistedEntities.size() != entities.size()) {
				return false;
			}

			for (int i = 0, batchStartIndex = 0; i<entities.size(); i++) {
				session.merge(entities.get(i));
				batchStartIndex = processBatchIfFull(session, entities, batchStartIndex, i);
			}

			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean remove(Session session, PK id)
			throws DataException {
		try {
			T entity = session.find(targetClass, id);
			if (entity == null) {
				return false;
			}

			session.remove(entity);
			session.flush();
			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean batchRemove(Session session, List<PK> ids) 
			throws DataException {
		try {
			List<T> entities = session
					.byMultipleIds(targetClass)
					.multiLoad(ids);

			if (entities.isEmpty()) {
				return false;
			}

			for (int i = 0, batchStartIndex = 0; i < entities.size(); i++) {
				T entity = entities.get(i);
				if (entity == null) {
					continue;
				}
				session.remove(entity);
				batchStartIndex = processBatchIfFull(
						session, entities, batchStartIndex, i);
			} 

			return true;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected List<PK> getIdentifiers(List<T> entities) {
		return getIdentifiers(null, entities, 0, entities.size());
	}

	@SuppressWarnings("unchecked")
	private List<PK> getIdentifiers(Session session, List<T> entities, int startIndex, int endIndex) {
		List<PK> identifiers = new ArrayList<>();
		for (int i = startIndex; i < endIndex; i++) {
			identifiers.add(session == null ? 
					entities.get(i).getId() :
						(PK) session.getIdentifier(entities.get(i)));
		}
		return identifiers;
	}

	private int processBatchIfFull(Session session, List<T> entities, int batchStartIndex, int currentIndex) {
		return processBatchIfFull(session, entities, null, batchStartIndex, currentIndex);
	}

	private int processBatchIfFull(Session session, List<T> entities, 
			List<PK> storedIdentifiers, int batchStartIndex, int currentIndex) {

		if (!(currentIndex == entities.size())
				&& (!((currentIndex - batchStartIndex) % BATCH_SIZE == 0))) {
			return batchStartIndex;
		}

		session.flush();
		if (storedIdentifiers != null) {
			storedIdentifiers.addAll(getIdentifiers(session, entities, batchStartIndex, currentIndex));
		}
		session.clear();

		return currentIndex +1;
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
	
	protected T findSingleResultBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildFindByQuery(session, criteria);
			List<T> results = session.createQuery(query).getResultList();
			return results.isEmpty() ? null : results.get(0);
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

			ScrollableResults<T> scrollableResults = 
					session.createQuery(query).scroll(ScrollMode.SCROLL_INSENSITIVE);
			results.setPage(getPage(scrollableResults, pos, pageSize));
			results.setResultCount(getResultCount(scrollableResults));

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

		if (criteria != null) {
			Path<T> orderBy = root.get(criteria.getOrderBy());
			query.orderBy(
					criteria.getAscDesc() == AbstractCriteria.ASC ? 
							builder.asc(orderBy) : builder.desc(orderBy));
		}
		return query;
	}

	protected Class<T> getTargetClass() {
		return targetClass;
	}

	/**
	 * Specify criteria for the queries performed by the {@link #findBy(Session, AbstractCriteria)} 
	 * and {@link #findBy(Session, AbstractCriteria, int, int)} methods.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param query The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param criteria Criteria object containing the clauses' values
	 */
	protected abstract void setFindByCriteria(CriteriaBuilder builder,
			CriteriaQuery<T> query, Root<T> root, AbstractCriteria<T> criteria);

}
