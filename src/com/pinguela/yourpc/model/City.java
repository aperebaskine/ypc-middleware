package com.pinguela.yourpc.model;

public class City 
extends AbstractTerritory {

	private Integer id;
	private Integer provinceId;

	public City() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

}
