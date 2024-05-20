package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.List;

public class Customer
extends AbstractPerson {
	
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
