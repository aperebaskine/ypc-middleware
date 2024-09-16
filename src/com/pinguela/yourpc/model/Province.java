package com.pinguela.yourpc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Province 
extends AbstractTerritory<Integer> {
	
	@ManyToOne
	@JoinColumn(name = "COUNTRY_ID")
	private Country country;

	public Province() {
		super();
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
