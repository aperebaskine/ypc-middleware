package com.pinguela.yourpc.model.dto;

public class ProvinceDTO 
extends AbstractTerritoryDTO<Integer> {
	
	private String countryId;

	protected String getCountryId() {
		return countryId;
	}

	protected void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
