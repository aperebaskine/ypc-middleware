package com.pinguela.yourpc.model;

public class City 
extends AbstractTerritory<Integer> {

	private Integer provinceId;

	public City() {
		super();
	}
	
	public City(Integer id, String name) {
		super(id, name);
	}
	
	public City(Integer id) {
		super(id);
	}
	
	public Integer getProvinceId() {
		return provinceId;
	}
	
	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

}
