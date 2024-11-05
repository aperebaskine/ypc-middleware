package com.pinguela.yourpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.dto.AbstractDTO;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryBuilder {
	
	Class<? extends AbstractDTO<?, ?>>[] dto();
	QueryType type();

}
