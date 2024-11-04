package com.pinguela.yourpc.dao.impl.builder;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.reflections.Reflections;

import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.ReflectionUtils;

@SuppressWarnings("rawtypes")
public class QueryBuilderDispatcher {

	private static Logger logger = LogManager.getLogger(QueryBuilderDispatcher.class);
	
	private static QueryBuilderDispatcher INSTANCE = new QueryBuilderDispatcher();

	private static final Map<Class<? extends AbstractDTO<?>>, AbstractQueryBuilder<?, ?, ?>> BUILDERS = new ConcurrentHashMap<>();
	
	static {
		initialize();
	}

	private static final void initialize() {
		Reflections reflections = new Reflections(AbstractQueryBuilder.class.getPackageName());

		for (Class<? extends AbstractQueryBuilder> clazz :
			reflections.getSubTypesOf(AbstractQueryBuilder.class)) {
			
			if (Modifier.isFinal(clazz.getModifiers())) {
				register(clazz);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private static final void register(Class<? extends AbstractQueryBuilder> builder) {
		Type dtoType = builder.getTypeParameters()[1].getBounds()[0];
		Class<? extends AbstractDTO<?>> dtoClass = (Class<? extends AbstractDTO<?>>) ReflectionUtils.getClass(dtoType);
		
		BUILDERS.put(dtoClass, (AbstractQueryBuilder<?, ?, ?>) ReflectionUtils.instantiate(builder));
	}
	
	public static QueryBuilderDispatcher getInstance() {
		return INSTANCE;
	}
	
	private QueryBuilderDispatcher() {
	}
	
	public AbstractQueryBuilder<?, ?, ?> dispatch(Class<? extends AbstractDTO<?>> targetDtoClass) {
		if (!BUILDERS.containsKey(targetDtoClass)) {
			String error = String.format("No query builder registered for DTO class {}.", targetDtoClass.getName());
			logger.fatal(error);
			throw new IllegalArgumentException(error);
		}
		
		return BUILDERS.get(targetDtoClass);
	}

}
