package com.pinguela.yourpc.model;

public interface EntityCriteria<PK, T> 
extends Criteria<T> {
	
	PK getId();
	
	void setId(PK id);

}