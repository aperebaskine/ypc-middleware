package com.pinguela.yourpc.model;

public class AttributeStatistics<T> 
extends AbstractValueObject {
	
	private String name;
	private T value;
	private Integer quantity;
	
	public AttributeStatistics(String name, T value, Integer quantity) {
		super();
		this.name = name;
		this.value = value;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public T getValue() {
		return value;
	}

	public Integer getQuantity() {
		return quantity;
	}

}
