package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.AbstractTerritory;

public abstract class AbstractTerritoryDTO<PK extends Comparable<PK>, T extends AbstractTerritory<PK>> 
extends AbstractDTO<T> {

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
