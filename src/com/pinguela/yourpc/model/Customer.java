package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Customer
extends AbstractPerson {
	
	@OneToMany(mappedBy = "customer")
	private List<Address> addresses = null;
	
	public Customer() {
		addresses = new ArrayList<Address>();
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addressList) {
		this.addresses = addressList;
	}

}
