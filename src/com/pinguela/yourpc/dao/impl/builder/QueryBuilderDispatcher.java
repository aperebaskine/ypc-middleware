package com.pinguela.yourpc.dao.impl.builder;

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

public class QueryBuilderDispatcher {

	private static Logger logger = LogManager.getLogger(QueryBuilderDispatcher.class);

	private static final QueryBuilderDispatcher INSTANCE = new QueryBuilderDispatcher();

	private static final Map<MultiKey<?>, AbstractQueryBuilder<?, ?, ?, ?>> BUILDERS = new ConcurrentHashMap<>();

	static {
		initialize();
	}

	private QueryBuilderDispatcher() {
	}

	public static QueryBuilderDispatcher getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("rawtypes")
	private static final void initialize() {
		Reflections reflections = new Reflections(AbstractQueryBuilder.class.getPackageName());

		for (Class<? extends AbstractQueryBuilder> subclass :
			reflections.getSubTypesOf(AbstractQueryBuilder.class)) {

			if (subclass.isAnnotationPresent(QueryBuilder.class)) {
				register(subclass);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void register(Class<? extends AbstractQueryBuilder> builderClass) {

		QueryBuilder annotation = builderClass.getAnnotation(QueryBuilder.class);
		AbstractQueryBuilder<?, ?, ?, ?> builder = ReflectionUtils.instantiate(builderClass);

		for (Class<? extends AbstractDTO<?, ?>> dtoClass : annotation.dto()) {
			MultiKey<?> key = new MultiKey<>(dtoClass, annotation.type());
			BUILDERS.put(key, builder);
		}
	}

	public <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> AbstractQueryBuilder<PK, E, D, C>
	dispatchSelectionQueryBuilder(Class<D> targetDtoClass) {
		return dispatch(targetDtoClass, QueryType.SELECT);
	}

	public <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> AbstractQueryBuilder<PK, E, D, C>
	dispatchMutationQueryBuilder(Class<D> targetDtoClass, QueryType queryType) {
		return dispatch(targetDtoClass, queryType);
	}

	@SuppressWarnings("unchecked")
	private <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> AbstractQueryBuilder<PK, E, D, C> 
	dispatch(Class<D> targetDtoClass, QueryType type) {

		MultiKey<?> key = new MultiKey<>(targetDtoClass, type);

		if (!BUILDERS.containsKey(key)) {
			String error = String.format("No query builder registered for DTO class %s and query type %s.", targetDtoClass.getName(), type.name());
			logger.fatal(error);
			throw new IllegalArgumentException(error);
		}

		return (AbstractQueryBuilder<PK, E, D, C>) BUILDERS.get(key);
	}

}
