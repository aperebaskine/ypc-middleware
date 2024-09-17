package com.pinguela.yourpc.model;

@SuppressWarnings("serial")
public abstract class AbstractEntityCriteria<PK, T>
extends AbstractCriteria<T>
implements EntityCriteria<PK, T> {
	
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
