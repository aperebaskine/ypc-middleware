package com.pinguela.yourpc.model;

public class SimpleUpdateValue<T> 
extends AbstractUpdateValues<T> {
	
	private Object value;

	public SimpleUpdateValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

}
