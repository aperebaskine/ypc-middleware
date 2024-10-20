package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractUpdateValues;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;

public abstract class AbstractMutableDAO<PK extends Comparable<PK>, T extends AbstractEntity<PK>>
extends AbstractDAO<PK, T> {

	private static final int BATCH_SIZE = 50;

	private static Logger logger = LogManager.getLogger(AbstractMutableDAO.class);

	@SuppressWarnings("unchecked")
	protected PK createEntity(Session session, T entity) 
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

	protected List<PK> createBatch(Session session, List<T> entities)
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

	protected boolean updateEntity(Session session, T entity) 
			throws DataException {
		try {
			T persistedEntity = session.find(getTargetClass(), entity.getId());
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

	protected boolean updateBatch(Session session, List<T> entities)
			throws DataException {
		try {
			List<T> persistedEntities = session
					.byMultipleIds(getTargetClass())
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

	protected boolean updateBy(Session session, AbstractCriteria<T> criteria, AbstractUpdateValues<T> updateValues) 
			throws DataException {
		try {
			CriteriaUpdate<T> updateQuery = buildCriteriaUpdate(session, criteria, updateValues);
			return session.createMutationQuery(updateQuery).executeUpdate() > 0;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean deleteEntity(Session session, PK id)
			throws DataException {
		try {
			T entity = session.find(getTargetClass(), id);
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

	protected boolean deleteBatch(Session session, List<PK> ids) 
			throws DataException {
		try {
			List<T> entities = session
					.byMultipleIds(getTargetClass())
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
	
	protected boolean deleteBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaDelete<T> deleteQuery = buildCriteriaDelete(session, criteria);
			return session.createMutationQuery(deleteQuery).executeUpdate() > 0;
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	private CriteriaUpdate<T> buildCriteriaUpdate(Session session, AbstractCriteria<T> criteria,
			AbstractUpdateValues<T> updateValues) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaUpdate<T> updateQuery = builder.createCriteriaUpdate(getTargetClass());
		Root<T> root = updateQuery.from(getTargetClass());

		setUpdateValues(builder, updateQuery, root, updateValues);

		updateQuery.where(buildWhereClause(builder, root, criteria));
		return updateQuery;
	}

	private CriteriaDelete<T> buildCriteriaDelete(Session session, AbstractCriteria<T> criteria) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaDelete<T> deleteQuery = builder.createCriteriaDelete(getTargetClass());
		Root<T> root = deleteQuery.from(getTargetClass());

		deleteQuery.where(buildWhereClause(builder, root, criteria));
		return deleteQuery;
	}

	private List<PK> getIdentifiers(List<T> entities) {
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

	/**
	 * Specify update values for the queries performed by the 
	 * {@link #updateBy(Session, AbstractCriteria, AbstractUpdateValues)} method.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param updateQuery The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param updateValues Criteria object containing the values to set
	 */
	protected abstract void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<T> updateQuery,
			Root<T> root, AbstractUpdateValues<T> updateValues);

}
