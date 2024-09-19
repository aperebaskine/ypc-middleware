package com.pinguela.yourpc.model;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class NullObject {

	private static Set<String> objectMethods;

	static {
		objectMethods = new HashSet<String>();
		for (Method method : Object.class.getMethods()) {
			objectMethods.add(method.getName());
		}
	}

	private static NullObjectMethodInterceptor interceptor = new NullObjectMethodInterceptor();

	private static Map<Class<?>, Object> defaultReturnValues;
	private static Map<Method, Object> customReturnValues;
	private static Map<Class<?>, Object> instances;

	static {
		defaultReturnValues = new HashMap<>();
		customReturnValues = new HashMap<>();
		instances = new HashMap<>();

		defaultReturnValues.put(void.class, null);
		defaultReturnValues.put(boolean.class, false);
		defaultReturnValues.put(byte.class, (byte) 0);
		defaultReturnValues.put(char.class, '\u0000');
		defaultReturnValues.put(short.class, (short) 0);
		defaultReturnValues.put(int.class, 0);
		defaultReturnValues.put(long.class, 0L);
		defaultReturnValues.put(float.class, 0.0f);
		defaultReturnValues.put(double.class, 0.0);

		defaultReturnValues.put(String.class, "");
		defaultReturnValues.put(Object.class, null);
		defaultReturnValues.put(List.class, Collections.emptyList());
		defaultReturnValues.put(Set.class, Collections.emptySet());
		defaultReturnValues.put(Map.class, Collections.emptyMap());
	}

	private NullObject() {
	}

	public static <T> T getInstance(Class<T> clazz) {
		return getInstance(clazz, null, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> target, Method[] customReturnValueMethods, Object[] customReturnValues) {

		if (Modifier.isFinal(target.getModifiers())) {
			throw new IllegalArgumentException("Cannot create null object for a final class.");
		}

		T instance = null;

		try {
			if (instances.containsKey(target)) {
				return (T) instances.get(target);
			}

			if (target.isInterface()) {
				return (T) Proxy.newProxyInstance(target.getClassLoader(), new Class<?>[] {target}, interceptor);
			}

			try {
				target.getDeclaredConstructor();
			} catch (NoSuchMethodException e) {
				throw new IllegalArgumentException(String.format(
						"Cannot create null object: class %s doesn't provide a no-arg constructor.", target.getName()));
			}

			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(target);
			enhancer.setCallback(interceptor);
			instance = (T) enhancer.create();

		} finally {
			if (instance != null) {
				setCustomReturnValues(customReturnValueMethods, customReturnValues);
				instances.put(target, instance);
			}
		}

		return instance;
	}

	public static void setCustomReturnValues(Method[] methods, Object[] returnValues) {

		if (methods == null || returnValues == null) {
			return;
		}

		if (methods.length != returnValues.length) {
			throw new IllegalArgumentException("Class and value arrays must be of equal size.");
		}

		Map<Method, Object> returnValueMap = new HashMap<>();

		for (int i = 0; i < methods.length; i++) {

			Method method = methods[i];
			Object returnValue = returnValues[i];

			if (!method.getReturnType().isInstance(returnValue)) {
				throw new IllegalArgumentException(
						String.format("Invalid return type %s for method %s, expecting return type %s.", 
								returnValue.getClass().getName(), method.getName(), method.getReturnType().getName()));
			} 

			returnValueMap.put(method, returnValue);
		}

		customReturnValues.putAll(returnValueMap);
	}

	private static class NullObjectMethodInterceptor 
	implements MethodInterceptor, java.lang.reflect.InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			return intercept(proxy, method, args, null);
		}

		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {

			if (objectMethods.contains(method.getName())) {
				return proxy != null ? proxy.invokeSuper(obj, args) : method.invoke(obj, args);
			}

			if (customReturnValues.containsKey(method)) {
				return customReturnValues.get(method);
			}

			Class<?> returnType = method.getReturnType();

			if (defaultReturnValues.containsKey(returnType)) {
				return defaultReturnValues.get(returnType);
			}

			try {
				return getInstance(returnType);
			} catch (Exception e) {
				return null;
			}
		}

	}

}
