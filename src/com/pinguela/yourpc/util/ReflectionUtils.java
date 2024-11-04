package com.pinguela.yourpc.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Objects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ReflectionUtils {
	
	private static Logger logger = LogManager.getLogger(ReflectionUtils.class);
	
	public static Class<?> getClass(Type type) {
		Class<?> clazz = null;
		try {
			clazz = Class.forName(getRawTypeName(type));
		} catch (ClassNotFoundException e) {
			// This exception should never be thrown as the Type object already represents a class
		}
		return clazz;
	}

	public static String getRawTypeName(Type type) {
		return type instanceof ParameterizedType ?
				((ParameterizedType) type).getRawType().getTypeName() :
					type.getTypeName();
	}

	public static Type[] getTypeParameterBounds(Class<?> clazz) {

		TypeVariable<?>[] typeVariables = clazz.getTypeParameters();

		if (typeVariables.length == 0) {
			Type superclassType = clazz.getGenericSuperclass();

			if (superclassType == null) {
				return new Type[0];
			}

			if (superclassType instanceof ParameterizedType) {
				Type[] superclassTypeArguments = ((ParameterizedType) superclassType).getActualTypeArguments();
				if (superclassTypeArguments.length > 0) {
					return superclassTypeArguments;
				}
			}
			// Find the closest class in the upper hierarchy with type parameters
			return getTypeParameterBounds(getClass(superclassType));
		}
		return typeVariables[0].getBounds();
	}
	
	public static <T> T instantiate(Class<T> target, Object... args) {
		try {
			return target.getDeclaredConstructor(getClasses(args)).newInstance(args);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			logger.error("{} thrown while instantiating {}. Message: {}",
					e.getClass().getName(), target.getName(), e.getMessage(), e);
			throw new IllegalArgumentException(e);
		}
	}
	
	private static Class<?>[] getClasses(Object... objects) {
		Class<?>[] classes = new Class<?>[objects.length];
		
		for (int i = 0; i < objects.length; i++) {
			classes[i] = Objects.requireNonNull(objects[i], "Cannot instantiate object using null arguments.").getClass();
		}
		
		return classes;
	}

}
