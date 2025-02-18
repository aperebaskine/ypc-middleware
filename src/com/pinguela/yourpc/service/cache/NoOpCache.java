package com.pinguela.yourpc.service.cache;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

import com.pinguela.CacheException;

public class NoOpCache<K, V> extends Cache<K, V> {
	
	@SuppressWarnings("rawtypes")
	private static final NoOpCache INSTANCE = new NoOpCache();

	private NoOpCache() {
		super(null);
	}
	
	@SuppressWarnings("unchecked")
	static <K, V> NoOpCache<K, V> getInstance() {
		return INSTANCE;
	}

	@Override
	public void forEach(BiConsumer<? super K, ? super V> action) throws CacheException {}

	@Override
	public V get(K key) throws CacheException {
		return null;
	}

	@Override
	public void put(K key, V value) throws CacheException {}

	@Override
	public boolean containsKey(K key) throws CacheException {
		return false;
	}

	@Override
	public void remove(K key) throws CacheException {}

	@Override
	public Map<K, V> getAll(Set<? extends K> keys) throws CacheException {
		return Collections.emptyMap();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> entries) throws CacheException {}

	@Override
	public void removeAll(Set<? extends K> keys) throws CacheException {}

	@Override
	public void clear() throws CacheException {}

	@Override
	public V computeIfAbsent(K key, Function<K, ? extends V> valueSupplier) {
		return valueSupplier.apply(key);
	}

	@Override
	public boolean remove(K key, V value) throws CacheException {
		return false;
	}
	
}
