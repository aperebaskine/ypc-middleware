package com.pinguela.yourpc.model;

public abstract class AbstractTerritory
extends AbstractValueObject {
	
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
