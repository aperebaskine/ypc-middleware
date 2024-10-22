package com.pinguela.yourpc.model.dto;

public abstract class AbstractTerritoryDTO<PK> 
extends AbstractDTO {

	private PK id;
	private String name;

	public PK getId() {
		return id;
	}
	public void setId(PK id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
