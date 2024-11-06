package com.pinguela.yourpc.dao.impl.builder;

import java.util.List;
import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.yourpc.annotation.QueryBuilder;
import com.pinguela.yourpc.dao.builder.MutationQueryBuilder;
import com.pinguela.yourpc.dao.builder.SelectionQueryBuilder;
import com.pinguela.yourpc.dao.impl.AbstractDAO;
import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;

/**
 * Base class for implementing builders for SQL queries to be executed by Data Access Objects.
 * This class provides method definitions for all expected query-building scenarios.
 * <p>
 * All methods are optional. Subclasses must override only the ones that they are expected to use.
 * </p>
 * <p>
 * By default, all methods throw {@link UnsupportedOperationException}, as a fail-safe for incorrect
 * method calls.
 * </p>
 * <p>
 * In order to be automatically registered with the {@link QueryBuilderDispatcher}, subclasses must
 * also be annotated with {@link QueryBuilder} specifying the DTO type handled by the class as well 
 * as the {@link QueryType}(s) supported, and also provide a no-argument constructor.
 * </p>
 * @param <PK> Primary key type of the entity
 * @param <E> The entity handled by the queries
 * @param <D> The Data Transfer Object returned by the queries
 * @param <C> A criteria object for performing queries based on specific criteria
 * 
 * @see QueryBuilderDispatcher
 * @see AbstractDAO
 */
public abstract class AbstractQueryBuilder<PK extends Comparable<PK>,
		E extends AbstractEntity<PK>,
		D extends AbstractDTO<PK, E>,
		C extends AbstractEntityCriteria<PK, E>> 
implements SelectionQueryBuilder<PK, E, D, C> , MutationQueryBuilder<PK, E, D, C> {

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

	@Override
	public Query<D> buildSelectionQuery(Session session, PK id, Locale locale) {
		throw unsupported();
	}

	@Override
	public Query<D> buildSelectionQuery(Session session, C criteria) {
		throw unsupported();
	}

	@Override
	public Query<D> buildSelectionQuery(Session session, C criteria, Integer pos, Integer pageSize) {
		throw unsupported();
	}

	@Override
	public Query<D> buildUpsertQuery(Session session, D dto) {
		throw unsupported();
	}

	@Override
	public Query<D> buildUpsertQuery(Session session, List<D> dtoList) {
		throw unsupported();
	}

	@Override
	public Query<D> buildUpdateByCriteriaQuery(Session session, D dto, C criteria) {
		throw unsupported();
	}

	@Override
	public Query<D> buildDeleteByCriteriaQuery(Session session, C criteria) {
		throw unsupported();
	}

	private static final RuntimeException unsupported() {
		throw new UnsupportedOperationException("Unsupported or unimplemented method called.");
	}

}
