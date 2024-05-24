package com.pinguela.yourpc.model;

public class Province 
extends AbstractTerritory<Integer> {
	
	private String countryId;

	public Province() {
	}

	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
