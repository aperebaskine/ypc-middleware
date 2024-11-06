package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.HibernateUtils;

public abstract class AbstractMutableDAO<PK extends Comparable<PK>, E extends AbstractEntity<PK>>
extends AbstractDAO<PK, E> {

	private static final int BATCH_SIZE = 50;

	private static Logger logger = LogManager.getLogger(AbstractMutableDAO.class);
	
	private String tableName;
		
	protected AbstractMutableDAO() {
	}

	@SuppressWarnings("unchecked")
	protected PK createEntity(Session session, E entity) 
			throws DataException {
		try {
			session.persist(entity);
			session.flush();
			return (PK) session.getIdentifier(entity);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected List<PK> createBatch(Session session, List<E> entities)
			throws DataException {
		try {
			List<PK> identifiers = new ArrayList<>();

			for (int i = 0, batchStartIndex = 0; i<entities.size(); i++) {
				session.persist(entities.get(i));
				batchStartIndex = processBatchIfFull(
						session, entities, identifiers, batchStartIndex, i);
			}

			return identifiers;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}	
	}

	protected boolean updateEntity(Session session, E entity) 
			throws DataException {
		try {
			E persistedEntity = session.find(getTargetClass(), entity.getId());
			if (persistedEntity == null) {
				return false;
			}

			session.merge(entity);
			session.flush();
			return true;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean updateBatch(Session session, List<E> entities)
			throws DataException {
		try {
			List<E> persistedEntities = session
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
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}
	
	protected boolean softDeleteEntity(Session session, String columnName, PK id) 
			throws DataException {
		try {
			if (tableName == null) {
				tableName = HibernateUtils.getTableName(getTargetClass());
			}
			
			String queryStr = String.format(" UPDATE %1$s SET %2$s = :date where ID = :id", tableName, columnName);
			return session.createNativeQuery(queryStr, getTargetClass())
					.setParameter("date", new Date())
					.setParameter("id", id)
					.executeUpdate() > 0;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@SuppressWarnings("unchecked")
	protected boolean updateBy(Session session, AbstractDTO<PK, E> dto, AbstractEntityCriteria<PK, E> criteria) 
			throws DataException {
		try {
			return getMutationQueryBuilder(session, dto.getClass(), QueryType.UPDATE)
					.buildUpsertQuery(session, dto)
					.executeUpdate() > 0;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean deleteEntity(Session session, PK id)
			throws DataException {
		try {
			E entity = session.find(getTargetClass(), id);
			if (entity == null) {
				return false;
			}

			session.remove(entity);
			session.flush();
			return true;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected boolean deleteBatch(Session session, List<PK> ids) 
			throws DataException {
		try {
			List<E> entities = session
					.byMultipleIds(getTargetClass())
					.multiLoad(ids);

			if (entities.isEmpty()) {
				return false;
			}

			for (int i = 0, batchStartIndex = 0; i < entities.size(); i++) {
				E entity = entities.get(i);
				if (entity == null) {
					continue;
				}
				session.remove(entity);
				batchStartIndex = processBatchIfFull(
						session, entities, batchStartIndex, i);
			} 

			return true;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}
	
	protected boolean deleteBy(Session session, AbstractEntityCriteria<PK, E> criteria) 
			throws DataException {
		try {
			return getMutationQueryBuilder(session, null, QueryType.DELETE)
					.buildDeleteByCriteriaQuery(session, criteria)
					.executeUpdate() > 0;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	private List<PK> getIdentifiers(List<E> entities) {
		return getIdentifiers(null, entities, 0, entities.size());
	}

	@SuppressWarnings("unchecked")
	private List<PK> getIdentifiers(Session session, List<E> entities, int startIndex, int endIndex) {
		List<PK> identifiers = new ArrayList<>();
		for (int i = startIndex; i < endIndex; i++) {
			identifiers.add(session == null ? 
					entities.get(i).getId() :
						(PK) session.getIdentifier(entities.get(i)));
		}
		return identifiers;
	}

	private int processBatchIfFull(Session session, List<E> entities, int batchStartIndex, int currentIndex) {
		return processBatchIfFull(session, entities, null, batchStartIndex, currentIndex);
	}

	private int processBatchIfFull(Session session, List<E> entities, 
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
	
}
