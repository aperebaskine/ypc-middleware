package com.pinguela.yourpc.model.dto;

public class CityDTO
extends AbstractTerritoryDTO<Integer> {

	private Integer provinceId;

	protected Integer getProvinceId() {
		return provinceId;
	}

	protected void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}
	
}
