package com.pinguela.yourpc.model.dto;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.reflections.Reflections;

public final class AttributeDTOFactory {
	
	/**
	 * Maps type parameter classes to their corresponding subclasses.
	 */
	private static final Map<Class<?>, Class<?>> SUBCLASSES;
	
	/**
	 * <p>Maps the SQL data type primary key (returned by {@link #getDataTypeIdentifier()})
	 * to the name of the parameterised class used by the attribute instance.</p>
	 * <p><b>This map must contain an entry for every parameterised type used in
	 * a subclass of this abstract factory.</b></p>
	 */
	public static final Map<String, Class<?>> TYPE_PARAMETER_CLASSES;

	static {
		Map<Class<?>, Class<?>> subclassMap = new HashMap<>();
		Map<String, Class<?>> typeParameterClassMap = new HashMap<>();

		Reflections reflections = new Reflections(AttributeDTO.class.getPackageName());
		for (Class<?> subclass : reflections.getSubTypesOf(AttributeDTO.class)) {
			
			if (!Modifier.isFinal(subclass.getModifiers())) {
				continue;
			}
			
			try {
				AttributeDTO<?> attribute = (AttributeDTO<?>) subclass.getDeclaredConstructor().newInstance();
				Class<?> typeParameterClass = attribute.getTypeParameterClass();

				subclassMap.put(typeParameterClass, subclass);
				typeParameterClassMap.put(attribute.getDataTypeIdentifier(), typeParameterClass);
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}
		
		SUBCLASSES = Collections.unmodifiableMap(subclassMap);
		TYPE_PARAMETER_CLASSES = Collections.unmodifiableMap(typeParameterClassMap);
	}
	
	public static final AttributeDTO<?> getInstance(String dataType) {
		return getInstance(TYPE_PARAMETER_CLASSES.get(dataType));
	}

	@SuppressWarnings("unchecked")
	public static final <E> AttributeDTO<E> getInstance(String name, E... values) {

		Class<E> typeParameter = (Class<E>) values.getClass().getComponentType();

		if (values == null || !TYPE_PARAMETER_CLASSES.containsValue(typeParameter)) {
			throw new IllegalArgumentException("Type of values must match one of the allowed type parameters.");
		}

		AttributeDTO<E> dto = getInstance(typeParameter);
		dto.setName(name);

		for (E value : values) {
			dto.addValue(null, value);
		}

		return dto;
	}

	@SuppressWarnings("unchecked")
	public static final <E> AttributeDTO<E> getInstance(Class<E> typeParameterClass) {

		if (!TYPE_PARAMETER_CLASSES.containsValue(typeParameterClass)) {
			throw new IllegalArgumentException(
					String.format("Cannot instantiate attribute using type parameter %s.", typeParameterClass.getName()));
		}

		try { 
			return (AttributeDTO<E>) SUBCLASSES.get(typeParameterClass).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(String.format("Exception thrown while creating instance: %s", e.getMessage()), e);
		}
	}
	
	private AttributeDTOFactory() {
	}

}
