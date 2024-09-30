package com.pinguela.yourpc.dao.impl;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.pinguela.yourpc.util.StringUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class AbstractDAO<PK extends Comparable<PK>, T extends AbstractEntity<PK>> {

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

	protected T findById(Session session, Object id) 
			throws DataException {
		try {
			return id == null ? null : session.find(targetClass, id);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}
	
	protected T findSingleResultBy(Session session, AbstractCriteria<T> criteria) 
			throws DataException {
		try {
			CriteriaQuery<T> query = buildFindByQuery(session, criteria);
			return session.createQuery(query).getSingleResultOrNull();
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

			try (ScrollableResults<T> scrollableResults = 
					session.createQuery(query).scroll(ScrollMode.SCROLL_INSENSITIVE)) {
				
				results.setPage(getPage(scrollableResults, pos, pageSize));
				results.setResultCount(getResultCount(scrollableResults));
			}
			
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
	
	protected Map<PK, T> mapByPrimaryKey(List<T> results) {
		Map<PK, T> map = new LinkedHashMap<>();
		for (T result : results) {
			map.put(result.getId(), result);
		}
		return map;
	}

	private CriteriaQuery<T> buildFindByQuery(Session session, AbstractCriteria<T> criteria) {
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(targetClass);
		Root<T> root = query.from(targetClass);

		if (criteria != null) {
			Predicate[] where = buildWhereClause(builder, root, criteria);
			if (where.length > 0) {
				query.where(where);
			}
			
			List<Order> orderBy = buildOrderByClause(session, builder, root, criteria);
			if (!orderBy.isEmpty()) {
				query.orderBy(orderBy);
			}
		}

		return query;
	}
	
	private List<Order> buildOrderByClause(Session session, CriteriaBuilder builder, 
			Root<T> root, AbstractCriteria<T> criteria) {
		List<Order> orderBy = new LinkedList<>();
		for (String pathStr : criteria.getOrderBy().keySet()) {
			
			String[] pathComponents = StringUtils.split(pathStr);
			Path<?> path = buildPath(root, pathComponents);
			
			orderBy.add(criteria.getOrderBy().get(pathStr) == AbstractCriteria.ASC ?
					builder.asc(path) : builder.desc(path));
		}
		return orderBy;
	}
	
	private Path<?> buildPath(Root<T> root, String... pathComponents) {
		Path<?> path = root;
		for (String component : pathComponents) {
			path = path.get(component);
		}
		return path;
	}
	
	protected final Predicate[] buildWhereClause(CriteriaBuilder builder,
			Root<T> root, AbstractCriteria<T> criteria) {
		List<Predicate> predicates = getCriteria(builder, root, criteria);
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	protected final Class<T> getTargetClass() {
		return targetClass;
	}

	/**
	 * Specify criteria for the queries performed by methods that create Query objects.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param query The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param criteria Object containing the criteria values
	 */
	protected abstract List<Predicate> getCriteria(CriteriaBuilder builder,
			Root<T> root, AbstractCriteria<T> criteria);
	
	/**
	 * Append the GROUP BY clause to the query performed by {@link #findBy(Session, AbstractCriteria)}
	 * or {@link #findBy(Session, AbstractCriteria, int, int)} based on the criteria provided.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param query The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param criteria Object containing the criteria values
	 */
	protected abstract void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<T> query,
			Root<T> root, AbstractCriteria<T> criteria);

}
