package com.pinguela.yourpc.dao.impl.builder;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.collections4.keyvalue.MultiKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.yourpc.annotation.QueryBuilder;
import com.pinguela.yourpc.dao.builder.MutationQueryBuilder;
import com.pinguela.yourpc.dao.builder.SelectionQueryBuilder;
import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.ReflectionUtils;

public class QueryBuilderDispatcher {

	private static Logger logger = LogManager.getLogger(QueryBuilderDispatcher.class);

	private static final QueryBuilderDispatcher INSTANCE = new QueryBuilderDispatcher();

	private static final Map<MultiKey<?>, BaseQueryBuilder<?, ?, ?, ?>> BUILDERS = new ConcurrentHashMap<>();

	static {
		initialize();
	}

	private QueryBuilderDispatcher() {
	}

	/**
	 * Return the Singleton instance of the dispatcher.
	 * @return The instance
	 */
	public static QueryBuilderDispatcher getInstance() {
		return INSTANCE;
	}

	@SuppressWarnings("rawtypes")
	private static final void initialize() {
		Reflections reflections = new Reflections(BaseQueryBuilder.class.getPackageName());

		for (Class<? extends BaseQueryBuilder> subclass :
			reflections.getSubTypesOf(BaseQueryBuilder.class)) {

			if (subclass.isAnnotationPresent(QueryBuilder.class)) {
				register(subclass);
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private static void register(Class<? extends BaseQueryBuilder> builderClass) {

		QueryBuilder annotation = builderClass.getAnnotation(QueryBuilder.class);
		BaseQueryBuilder<?, ?, ?, ?> builder = ReflectionUtils.instantiate(builderClass);

		for (QueryType queryType : annotation.type()) {
			MultiKey<?> key = new MultiKey<>(queryType, annotation.dto());
			BUILDERS.put(key, builder);
		}
	}

	public <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> SelectionQueryBuilder<PK, E, D, C>
	dispatchSelectionQueryBuilder(Class<D> targetDtoClass) {
		return dispatch(targetDtoClass, QueryType.SELECT);
	}

	public <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> MutationQueryBuilder<PK, E, D, C>
	dispatchMutationQueryBuilder(Class<D> targetDtoClass, QueryType queryType) {
		
		if (queryType == QueryType.SELECT) {
			throw new IllegalArgumentException("Invalid query type argument.");
		}
		
		return dispatch(targetDtoClass, queryType);
	}

	@SuppressWarnings("unchecked")
	private <PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>> BaseQueryBuilder<PK, E, D, C> 
	dispatch(Class<D> targetDtoClass, QueryType type) {

		MultiKey<?> key = new MultiKey<>(targetDtoClass, type);

		if (!BUILDERS.containsKey(key)) {
			String error = String.format("No query builder registered for DTO class %s and query type %s.", targetDtoClass.getName(), type.name());
			logger.fatal(error);
			throw new IllegalArgumentException(error);
		}

		return (BaseQueryBuilder<PK, E, D, C>) BUILDERS.get(key);
	}

}
