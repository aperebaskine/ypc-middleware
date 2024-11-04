package com.pinguela.yourpc.dao.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.impl.builder.QueryBuilderDispatcher;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.util.ReflectionUtils;

import jakarta.persistence.criteria.CriteriaQuery;

public abstract class AbstractDAO<PK extends Comparable<PK>, T extends AbstractEntity<PK>> {

	private static Logger logger = LogManager.getLogger(AbstractDAO.class);
	
	private static QueryBuilderDispatcher QUERY_BUILDER_DISPATCHER = QueryBuilderDispatcher.getInstance();

	private Class<T> targetClass;

	protected AbstractDAO() {
		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {
		Type targetClassType = ReflectionUtils.getTypeParameterBounds(this.getClass())[1];
		targetClass = (Class<T>) ReflectionUtils.getClass(targetClassType);
	}

	protected T findById(Session session, Object id) 
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

	protected T findSingleResultBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildQuery(session, criteria);
			return session.createQuery(query).getSingleResultOrNull();
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected List<T> findBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildQuery(session, criteria);
			return session.createQuery(query).getResultList();
		} catch (RuntimeException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	protected Results<T> findBy(Session session, AbstractCriteria<T> criteria, int pos, int pageSize) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildQuery(session, criteria);
			Results<T> results = new Results<T>();
			
			try (ScrollableResults<T> scrollableResults = 
					session.createQuery(query).scroll(ScrollMode.SCROLL_INSENSITIVE)) {

				results.setPage(getPage(scrollableResults, pos, pageSize));
				results.setResultCount(getResultCount(scrollableResults));
			}

			return results;
		} catch (RuntimeException e) {
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

	protected Map<PK, T> mapByPrimaryKey(List<T> results) {
		Map<PK, T> map = new LinkedHashMap<>();
		for (T result : results) {
			map.put(result.getId(), result);
		}
		return map;
	}

}
