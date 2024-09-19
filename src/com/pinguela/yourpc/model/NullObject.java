package com.pinguela.yourpc.model;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

public class NullObject {
	
	private static NullObjectInvocationHandler handler = new NullObjectInvocationHandler();
	
	private static Map<Class<?>, Object> defaultReturnValues;
	private static Map<Method, Object> customReturnValues;
	private static Map<Class<?>, Object> instances;
	
	static {
		defaultReturnValues = new HashMap<>();
		customReturnValues = new HashMap<>();
		instances = new HashMap<>();
		
		defaultReturnValues.put(boolean.class, false);
        defaultReturnValues.put(byte.class, (byte) 0);
        defaultReturnValues.put(char.class, '\u0000');
        defaultReturnValues.put(short.class, (short) 0);
        defaultReturnValues.put(int.class, 0);
        defaultReturnValues.put(long.class, 0L);
        defaultReturnValues.put(float.class, 0.0f);
        defaultReturnValues.put(double.class, 0.0d);
        defaultReturnValues.put(String.class, "");
        defaultReturnValues.put(Collection.class, Collections.EMPTY_LIST);
        defaultReturnValues.put(Map.class, Collections.EMPTY_MAP);
        defaultReturnValues.put(Set.class, Collections.EMPTY_SET);
	}
	
	private NullObject() {
	}
	
	public static <T> T getInstance(Class<T> clazz) {
		return getInstance(clazz, null, null);
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz, Method[] customReturnValueMethods, Object[] customReturnValues) {
		
		T instance = null;

		if (instances.containsKey(clazz)) {
			instance = (T) instances.get(clazz);
		} else {
			if (clazz.isInterface()) {
				instance = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, handler);
			} else {
				Enhancer enhancer = new Enhancer();
				enhancer.setSuperclass(clazz);
				enhancer.setCallback(handler);
				instance = (T) enhancer.create();
			}
			instances.put(clazz, instance);
		}
		
		setCustomReturnValues(customReturnValueMethods, customReturnValues);
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
			
			if (!method.getReturnType().isAssignableFrom(returnValue.getClass())) {
				throw new IllegalArgumentException(
						String.format("Incorrect return type %s for method %s", 
								returnValue.getClass().getName(), method.getName()));
			} 
			
			returnValueMap.put(method, returnValue);
		}
		
		customReturnValues.putAll(returnValueMap);
	}
	
	private static class NullObjectInvocationHandler 
	implements java.lang.reflect.InvocationHandler, InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			
			return customReturnValues.containsKey(method) ?
					customReturnValues.get(method) :
						defaultReturnValues.get(method.getReturnType());
		}
		
	}

}
