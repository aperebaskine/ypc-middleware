package com.pinguela.yourpc.model;

import java.util.List;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
@AttributeOverride(name = "id", column = @Column(columnDefinition = "CHAR(3)"))
public class Country 
extends AbstractTerritory<String> {

	public Country() {
	}
	
	@OneToMany(mappedBy = "country")
	private List<Province> provinces;

	public List<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(List<Province> provinces) {
		this.provinces = provinces;
	}
	
}