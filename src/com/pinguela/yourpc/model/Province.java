package com.pinguela.yourpc.model;

public class Province 
extends AbstractTerritory<Integer> {
	
	private String countryId;

	
	public Province() {
		super();
	}
	
	public Province(Integer id, String name) {
		super(id, name);
	}
	
	public Province(Integer id) {
		super(id);
	}
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
