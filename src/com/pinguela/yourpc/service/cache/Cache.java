package com.pinguela.yourpc.service.cache;

import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.ehcache.spi.loaderwriter.BulkCacheLoadingException;
import org.ehcache.spi.loaderwriter.BulkCacheWritingException;
import org.ehcache.spi.loaderwriter.CacheLoadingException;
import org.ehcache.spi.loaderwriter.CacheWritingException;

import com.pinguela.CacheException;

public class Cache<K, V> {

	public static final String ATTRIBUTES = "attributes";

	private org.ehcache.Cache<K, V> cache;
	
	Cache(org.ehcache.Cache<K, V> cache) {
		this.cache = cache;
	}

	public void forEach(BiConsumer<? super K, ? super V> action) throws CacheException {
		try {     
			cache.forEach((e) -> action.accept(e.getKey(), e.getValue()));
		} catch (Exception e) {
			throw new CacheException("Error iterating through cache entries", e);
		}
	}

	public V get(K key) throws CacheException {
		try {
			return cache.get(key);
		} catch (CacheLoadingException e) {
			throw new CacheException(String.format("Failed to load value for key: %s", key), e);
		}
	}

	public void put(K key, V value) throws CacheException {
		try {
			cache.put(key, value);
		} catch (CacheWritingException e) {
			throw new CacheException(String.format("Failed to put value for key: %s", key), e);
		}
	}

	public boolean containsKey(K key) throws CacheException {
		try {
			return cache.containsKey(key);
		} catch (Exception e) {
			throw new CacheException(String.format("Error checking existence of key: %s", key), e);
		}
	}

	public void remove(K key) throws CacheException {
		try {
			cache.remove(key);
		} catch (CacheWritingException e) {
			throw new CacheException(String.format("Failed to remove key: %s", key), e);
		}
	}

	public Map<K, V> getAll(Set<? extends K> keys) throws CacheException {
		try {
			return cache.getAll(keys);
		} catch (BulkCacheLoadingException e) {
			throw new CacheException(String.format("Failed to load multiple values for keys: %s", keys), e);
		}
	}

	public void putAll(Map<? extends K, ? extends V> entries) throws CacheException {
		try {
			cache.putAll(entries);
		} catch (BulkCacheWritingException e) {
			throw new CacheException("Failed to put multiple values", e);
		}
	}

	public void removeAll(Set<? extends K> keys) throws CacheException {
		try {
			cache.removeAll(keys);
		} catch (BulkCacheWritingException e) {
			throw new CacheException("Failed to remove multiple keys", e);
		}
	}

	public void clear() throws CacheException {
		try {
			cache.clear();
		} catch (Exception e) {
			throw new CacheException("Error clearing the cache", e);
		}
	}
	
	public V computeIfAbsent(K key, Function<K, ? extends V> valueSupplier) {
		
		V value = cache.get(key);
		
		if (value == null) {
			value = valueSupplier.apply(key);
			cache.put(key, value);
		}
		
		return value;
	}

	public boolean remove(K key, V value) throws CacheException {
		try {
			return cache.remove(key, value);
		} catch (CacheWritingException e) {
			throw new CacheException(String.format("Failed to remove key-value pair for key: %s", key), e);
		}
	}

}
