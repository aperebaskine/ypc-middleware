package com.pinguela.yourpc.model;

public class Province 
extends AbstractTerritory {

	private Integer id;
	private String countryId;

	public Province() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
