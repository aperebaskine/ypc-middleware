package com.pinguela.yourpc.dao.impl.builder;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;

public abstract class AbstractNativeQueryBuilder<PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> 
extends AbstractQueryBuilder<PK, E, D, C> {

	public AbstractNativeQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		super(dtoClass, entityClass);
	}

}
