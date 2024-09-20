package com.pinguela.yourpc.model;

import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractTerritory<PK>
extends AbstractEntity<PK> {
	
	private String name;
	
	public AbstractTerritory() {
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}	

}
