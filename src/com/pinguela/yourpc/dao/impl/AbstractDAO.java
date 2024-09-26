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
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class AbstractDAO<PK, T extends AbstractEntity<PK>> {

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

		if (criteria != null) {
			query.where(getCriteriaArray(builder, root, criteria));

			Path<T> orderBy = root.get(criteria.getOrderBy());
			query.orderBy(
					criteria.getAscDesc() == AbstractCriteria.ASC ? 
							builder.asc(orderBy) : builder.desc(orderBy));
		}

		return query;
	}
	
	protected final Predicate[] getCriteriaArray(CriteriaBuilder builder,
			Root<T> root, AbstractCriteria<T> criteria) {
		List<Predicate> criteriaList = 
				getCriteria(builder, root, criteria);
		return criteriaList.toArray(new Predicate[criteriaList.size()]);
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
