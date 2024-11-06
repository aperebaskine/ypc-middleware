package com.pinguela.yourpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.dto.AbstractDTO;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface QueryBuilder {
	
	Class<? extends AbstractEntity<?>> entity();
	Class<? extends AbstractDTO<?, ?>> dto();
	QueryType[] type();
	String mode() default "";

}
