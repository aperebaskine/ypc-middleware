package com.pinguela.yourpc.dao.impl.builder;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;

public abstract class AbstractNativeQueryBuilder<PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> 
extends BaseQueryBuilder<PK, E, D, C> {

	public AbstractNativeQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		super(dtoClass, entityClass);
	}
	
	protected abstract void select(StringBuilder query, C criteria);
	protected abstract void from(StringBuilder query, C criteria);
	protected abstract void where(StringBuilder query, C criteria);
	protected abstract void groupBy(StringBuilder query, C criteria);
	protected abstract void having(StringBuilder query, C criteria);

}
