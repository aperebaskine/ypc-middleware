package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.City;

public class CityDTO
extends AbstractTerritoryDTO<Integer, City> {

	private Integer provinceId;

	protected Integer getProvinceId() {
		return provinceId;
	}

	protected void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
}
