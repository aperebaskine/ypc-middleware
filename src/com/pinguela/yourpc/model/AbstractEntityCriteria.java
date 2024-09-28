package com.pinguela.yourpc.model;

public abstract class AbstractEntityCriteria<PK, T>
extends AbstractCriteria<T> {
	
	public static final String ORDER_BY_ID = "id";
	
	private PK id;
	
	public AbstractEntityCriteria() {
	}
	
	public PK getId() {
		return id;
	}
	
	public void setId(PK id) {
		this.id = id;
	}
	
}
