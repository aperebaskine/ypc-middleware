package com.pinguela.yourpc.dao.builder;

import java.util.Locale;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;

/**
 * Common interface for building database selection queries. All methods are optional and 
 * to be defined on a case-by-case basis according to the implementing class' requirements.
 * @param <PK> Primary key type of the entity
 * @param <E> The entity handled by the queries
 * @param <D> The Data Transfer Object returned by the queries
 * @param <C> A criteria object for performing queries based on specific criteria
 */
public interface SelectionQueryBuilder<PK extends Comparable<PK>,
		E extends AbstractEntity<PK>,
		D extends AbstractDTO<PK, E>,
		C extends AbstractEntityCriteria<PK, E>> {
	
	/**
	 * Build a selection query using a primary key and/or locale as parameters. 
	 * @param session The session used to interact with the database
	 * @param id (Optional) Primary key of the entity. Return all records if not provided
	 * @param locale (Optional) A locale object to specify, where necessary, which 
	 * internationalized strings to return
	 * @return A query object ready to be executed
	 */
	Query<D> buildSelectionQuery(Session session, PK id, Locale locale);
	
	/**
	 * Build a query to fetch a list of records based on specific criteria.
	 * @param session The session used to interact with the database
	 * @param criteria An object containing the criteria to filter
	 * @return A query object ready to be executed
	 */
	Query<D> buildSelectionQuery(Session session, C criteria);
	
	/**
	 * Build a paginated query to fetch a list of records based on specific criteria.
	 * @param session The session used to interact with the database
	 * @param criteria An object containing the criteria to filter
	 * @param pos The start position of the page to fetch
	 * @param pageSize The amount of records to return
	 * @return A query object ready to be executed
	 */
	Query<D> buildSelectionQuery(Session session, C criteria, Integer pos, Integer pageSize);
	
}
