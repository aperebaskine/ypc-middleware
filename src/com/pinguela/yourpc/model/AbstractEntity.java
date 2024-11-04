package com.pinguela.yourpc.model;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@SuppressWarnings("rawtypes")
public abstract class AbstractEntity<PK extends Comparable<PK>> 
extends AbstractValueObject {
		
	private static final Map<Class<? extends AbstractEntity>, Field[]> KEY_FIELDS = new ConcurrentHashMap<>();
	
	@SuppressWarnings("unchecked")
	private static Field[] fetchKeyFields(Class<? extends AbstractEntity> clazz) {
		
		Collection<Field> naturalIdFields = new LinkedHashSet<Field>();
		Collection<Field> allKeyFields = new LinkedHashSet<Field>();
		
		while (!AbstractEntity.class.equals(clazz)) {
			
			Field[] fields = clazz.getDeclaredFields();
			
			for (Field field : fields) {
				
				if (field.isAnnotationPresent(Id.class)
						|| field.isAnnotationPresent(Transient.class)) {
					continue;
				}
								
				if (field.isAnnotationPresent(NaturalId.class)) {
					naturalIdFields.add(field);
				} else if (naturalIdFields.isEmpty()) {
					allKeyFields.add(field);
				}
			}
			
			clazz = (Class<? extends AbstractEntity>) clazz.getSuperclass();
		}
		
		return naturalIdFields.isEmpty() ? 
				allKeyFields.toArray(new Field[allKeyFields.size()]) : 
					naturalIdFields.toArray(new Field[naturalIdFields.size()]);
	}
		
	@Override
	public int hashCode() {
		return getId() == null ?
				reflectionHashCode() : 
					Objects.hash(getId());
	}

	private int reflectionHashCode() {
		return Objects.hash(fetchKeyFieldValues());
	}

	private Object[] fetchKeyFieldValues() {
		Class<? extends AbstractEntity> thisClass = this.getClass();
		Field[] keyFields = KEY_FIELDS.computeIfAbsent(thisClass, key -> {
			return fetchKeyFields(thisClass);
		});
		
		Object[] values = new Object[keyFields.length];
		
		for (int i = 0; i < keyFields.length; i++) {
			values[i] = getValue(keyFields[i]);
		}
		
		return values;
	}
	
	private Object getValue(Field field) {
		try {
			return field.get(this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new AssertionError(e); // Should never be thrown
		}
	}

	@Override
	public boolean equals(Object obj) {
		
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!this.getClass().equals(obj.getClass())) {
			return false;
		}
		
		AbstractEntity<?> other = (AbstractEntity<?>) obj;
		
		if (getId() != null && other.getId() != null) {
			return Objects.equals(getId(), other.getId());
		}
		
		return Arrays.equals(fetchKeyFieldValues(), other.fetchKeyFieldValues());
	}
	
	public abstract PK getId();


}
