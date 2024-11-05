package com.pinguela.yourpc.dao.impl;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.impl.builder.AbstractQueryBuilder;
import com.pinguela.yourpc.dao.impl.builder.QueryBuilderDispatcher;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.ReflectionUtils;

public abstract class AbstractDAO<PK extends Comparable<PK>, E extends AbstractEntity<PK>> {

	private static Logger logger = LogManager.getLogger(AbstractDAO.class);

	private static final QueryBuilderDispatcher QUERY_BUILDER_DISPATCHER = QueryBuilderDispatcher.getInstance();

	private Class<E> targetClass;

	protected AbstractDAO() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		Type targetClassType = ReflectionUtils.getTypeParameterBounds(this.getClass())[1];
		targetClass = (Class<E>) ReflectionUtils.getClass(targetClassType);
	}

	protected Class<E> getTargetClass() {
		return targetClass;
	}

	protected E findById(Session session, PK id) 
			throws DataException {

		if (id == null) {
			logger.warn("findById called with null ID parameter.");
			return null;
		}

		try {
			return id == null ? null : session.find(targetClass, id);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} 
	}

	protected AbstractDTO<PK, E> findSingleResultBy(Session session, 
			Class<? extends AbstractDTO<PK, E>> targetDtoClass, AbstractEntityCriteria<PK, E> criteria) 
					throws DataException {
		try {
			return getQuery(session, targetDtoClass, criteria, QueryType.SELECT).getSingleResultOrNull();
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected <D extends AbstractDTO<PK, E>> List<D> findBy(Session session, Class<D> targetDtoClass,
			AbstractEntityCriteria<PK, E> criteria) throws DataException {
		try {
			return getQuery(session, targetDtoClass, criteria, QueryType.SELECT).getResultList();
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected <D extends AbstractDTO<PK, E>> Results<D> findBy(Session session, 
			Class<D> targetDtoClass, AbstractEntityCriteria<PK, E> criteria, int pos, int pageSize) 
					throws DataException {
		try {

			Query<D> query = getQuery(session, targetDtoClass, criteria, pos, pageSize);
			Results<D> results = new Results<>();

			results.setResultCount(Long.valueOf(query.getResultCount()).intValue());
			results.setPage(query.getResultList());

			return results;
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}
	
	protected <D extends AbstractDTO<PK, E>> Map<PK, D> mapByPrimaryKey(List<D> dtoList) {
		Map<PK, D> map = new TreeMap<>();
		for (D dto : dtoList) {
			map.put(dto.getId(), dto);
		}
		return map;
	}

	private <D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> AbstractQueryBuilder<PK, E, D, C> getQueryBuilder(
			Session session, Class<D> targetDtoClass, QueryType queryType) {
		return QUERY_BUILDER_DISPATCHER.dispatch(targetDtoClass, queryType);
	}

	protected <D extends AbstractDTO<PK, E>> Query<D> getQuery(Session session, 
			Class<D> targetDtoClass, AbstractEntityCriteria<PK, E> criteria, QueryType queryType) {
		return getQueryBuilder(session, targetDtoClass, queryType).buildQuery(session, criteria);
	}

	protected <D extends AbstractDTO<PK, E>> Query<D> getQuery(Session session, 
			Class<D> targetDtoClass, AbstractEntityCriteria<PK, E> criteria, int pos, int pageSize) {
		return getQueryBuilder(session, targetDtoClass, QueryType.SELECT).buildQuery(session, criteria, pos, pageSize);
	}

}
