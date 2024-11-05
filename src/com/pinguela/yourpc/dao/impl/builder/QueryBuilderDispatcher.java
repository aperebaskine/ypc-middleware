package com.pinguela.yourpc.dao.impl.builder;

import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.yourpc.annotation.QueryBuilder;
import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class QueryBuilderDispatcher {

	private static Logger logger = LogManager.getLogger(QueryBuilderDispatcher.class);
	
	private static final QueryBuilderDispatcher INSTANCE = new QueryBuilderDispatcher();

	private static final Map<MultiKey<?>, AbstractQueryBuilder<?, ?, ?, ?>> BUILDERS = new ConcurrentHashMap<>();
	
	static {
		initialize();
	}

	private static final void initialize() {
		Reflections reflections = new Reflections(AbstractQueryBuilder.class.getPackageName());

		for (Class<? extends AbstractQueryBuilder> clazz :
			reflections.getSubTypesOf(AbstractQueryBuilder.class)) {
			
			if (!Modifier.isFinal(clazz.getModifiers())) {
				continue;
			}
			
			if (validate(clazz)) {
				register(clazz);
			}
		}
	}
	
	private static boolean validate(Class<? extends AbstractQueryBuilder> target) {
		
		if (Modifier.isAbstract(target.getModifiers())) {
			return false;
		}
		
		if (!target.isAnnotationPresent(QueryBuilder.class)) {
			String errorMessage = String.format("Class %s must be annotated with @%s",
					target.getSimpleName(), QueryBuilder.class.getSimpleName());
			logger.fatal(errorMessage);
			throw new IllegalStateException(errorMessage);
		}
		
		return true;
	}

	private static void register(Class<? extends AbstractQueryBuilder> builderClass) {

		QueryBuilder annotation = builderClass.getAnnotation(QueryBuilder.class);
		AbstractQueryBuilder builder = ReflectionUtils.instantiate(builderClass);

		for (Class<? extends AbstractDTO<?, ?>> dtoClass : annotation.dto()) {
			MultiKey<?> key = new MultiKey<>(dtoClass, annotation.type());
			BUILDERS.put(key, builder);
		}
	}
	
	public static QueryBuilderDispatcher getInstance() {
		return INSTANCE;
	}
	
	private QueryBuilderDispatcher() {
	}
	
	@SuppressWarnings("unchecked")
	public <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> AbstractQueryBuilder<PK, E, D, C> dispatch(
			Class<D> targetDtoClass, QueryType type) {
		MultiKey<?> key = new MultiKey<>(targetDtoClass, type);
		
		if (!BUILDERS.containsKey(key)) {
			String error = String.format("No query builder registered for DTO class {}.", targetDtoClass.getName());
			logger.fatal(error);
			throw new IllegalArgumentException(error);
		}
		
		return (AbstractQueryBuilder<PK, E, D, C>) BUILDERS.get(key);
	}

}
