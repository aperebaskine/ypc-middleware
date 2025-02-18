package com.pinguela.yourpc.service.cache;

import java.lang.reflect.Type;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import com.pinguela.yourpc.config.ConfigManager;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.model.dto.AttributeDTO;

public class CacheManager implements AutoCloseable {
	
	private static final String ATTRIBUTE_CACHE_NAME = "cache.attributes.name";
	private static final String ATTRIBUTE_CACHE_MAX_SIZE = "cache.attributes.max_size";
	private static final String ATTRIBUTE_CACHE_ENABLE = "cache.attributes.enable";
	
	private static final CacheManager INSTANCE = new CacheManager();
	
	private org.ehcache.CacheManager cacheManager;
		
	public CacheManager() {
		init();
	}
	
	public static CacheManager getInstance() {
		return INSTANCE;
	}
	
	private void init() {
		CacheManagerBuilder<org.ehcache.CacheManager> builder =
				CacheManagerBuilder.newCacheManagerBuilder();
		
		boolean isAttributeCacheEnabled = 
				Boolean.valueOf(ConfigManager.getParameter(ATTRIBUTE_CACHE_ENABLE));
		
		if (isAttributeCacheEnabled) {
			int maxSize = Integer.valueOf(ConfigManager.getParameter(ATTRIBUTE_CACHE_MAX_SIZE));
			cacheDtoById(builder, ATTRIBUTE_CACHE_NAME, AttributeDTO.class, maxSize);
		}
		
		this.cacheManager = builder.build(true);
	}
	
	private <T extends AbstractDTO<?>> void cacheDtoById(CacheManagerBuilder<org.ehcache.CacheManager> builder, String name, 
			Class<T> dtoClass, int maxCacheSize) {
		
		Class<?> idClass = getIdType(dtoClass);
		CacheConfigurationBuilder<?, ?> configBuilder = CacheConfigurationBuilder.newCacheConfigurationBuilder
				(idClass, dtoClass, ResourcePoolsBuilder.heap(maxCacheSize));
		
		CacheConfiguration<?, ?> config = configBuilder.build();
		builder.withCache(name, config);
	}
	
	private <PK extends Comparable<PK>, T extends AbstractDTO<PK>> Class<PK> getIdType(
			Class<? extends AbstractDTO<?>> dtoClass) {
		Type current = dtoClass;
		// TODO: Finish
		return null;
	}
	
	public <K, V> Cache<K, V> getCache(String name, Class<K> keyClass, Class<V> valueClass) {
		org.ehcache.Cache<K, V> cache = cacheManager.getCache(name, keyClass, valueClass);
		
		return cache == null ?
				NoOpCache.getInstance() :
					new Cache<K, V>(cache);
	}
	
	@Override
	public void close() throws Exception {
		cacheManager.close();	
	}

}
