package com.pinguela.yourpc.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Province 
extends AbstractTerritory<Integer> {
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;
	
	@OneToMany(mappedBy = "province")
	private List<City> cities;

	public Province() {
		super();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public List<City> getCities() {
		return cities;
	}

	public void setCities(List<City> cities) {
		this.cities = cities;
	}

}
