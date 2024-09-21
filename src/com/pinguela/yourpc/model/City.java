package com.pinguela.yourpc.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class City 
extends AbstractTerritory<Integer> {

	@ManyToOne(optional = false)
	@JoinColumn(name = "PROVINCE_ID")
	private Province province;

	public City() {
	}

	public Province getProvince() {
		return province;
	}

	public void setProvince(Province province) {
		this.province = province;
	}

}
