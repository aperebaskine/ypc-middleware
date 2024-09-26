package com.pinguela.yourpc.model;

public class SimpleCriteria<T>
extends AbstractCriteria<T> {
	
	private Object value;

	public SimpleCriteria(Object value) {
		super();
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	@Override
	protected void setDefaultOrdering() {
		setOrderBy("id");
		setAscDesc(DESC);
	}

}
