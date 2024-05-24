package com.pinguela.yourpc.model;

public class City 
extends AbstractTerritory<Integer> {

	private Integer provinceId;

	public City() {
	}

	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

}
