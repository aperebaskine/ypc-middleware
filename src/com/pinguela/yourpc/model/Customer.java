package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer
extends User
implements Cloneable {

	private List<Address> addresses = null;

	public Customer() {
		addresses = new ArrayList<Address>();
		super.setRoleId("customer");
	}

	public void setRoleId() {}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addressList) {
		this.addresses = addressList;
	}

	@Override
	public Customer clone() {
		try {
			Customer clone = (Customer) super.clone();
			clone.setCreationDate(new Date(this.getCreationDate().getTime()));
			clone.setAddresses(new ArrayList<Address>(addresses));
			return clone;
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
	}

}
