package com.pinguela.yourpc.dao.builder;

import java.util.List;

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
 * @param <D> The Data Transfer Object containing values to insert or update
 * @param <C> A criteria object for performing operations based on specific criteria
 */
public interface MutationQueryBuilder<PK extends Comparable<PK>,
		E extends AbstractEntity<PK>,
		D extends AbstractDTO<PK, E>,
		C extends AbstractEntityCriteria<PK, E>>  {
	
	/**
	 * Build a query to insert (or update, if a primary key is present) a single record.
	 * @param session The session used to interact with the database
	 * @param dto Object containing the values to insert
	 * @return A query object ready to be executed
	 */
	Query<D> buildUpsertQuery(Session session, D dto);
	
	/**
	 * Build a query to insert (or update, if a primary key is present) multiple records.
	 * @param session The session used to interact with the database
	 * @param dtoList Collection of objects containing the values to insert
	 * @return A query object ready to be executed
	 */
	Query<D> buildUpsertQuery(Session session, List<D> dtoList);
	
	/**
	 * Build a query to update values in record that match specific criteria.
	 * @param session The session used to interact with the database
	 * @param dto The object containing the values to set
	 * @param criteria The object containing the criteria to filter
	 * @return A query object ready to be executed
	 */
	Query<D> buildUpdateByCriteriaQuery(Session session, D dto, C criteria);
	
	/**
	 * Build a query to delete records that match specific criteria.
	 * @param session The session used to interact with the database
	 * @param criteria The object containing the criteria to filter
	 * @return A query object ready to be executed
	 */
	Query<D> buildDeleteByCriteriaQuery(Session session, C criteria);
		
}
