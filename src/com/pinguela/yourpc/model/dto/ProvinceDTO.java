package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.Province;

public class ProvinceDTO 
extends AbstractTerritoryDTO<Integer, Province> {
	
	private String countryId;

	protected String getCountryId() {
		return countryId;
	}

	protected void setCountryId(String countryId) {
		this.countryId = countryId;
	}

}
