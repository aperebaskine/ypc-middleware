package com.pinguela.yourpc.model;

public class AddressUpdateValues
extends AbstractUpdateValues<Address> {
	
	private Integer addressId;
	private Boolean isDefault;
	private Boolean isBilling;
	
	public AddressUpdateValues(Integer addressId, Boolean isDefault, Boolean isBilling) {
		this.addressId = addressId;
		this.isDefault = isDefault;
		this.isBilling = isBilling;
	}
	
	public Integer getAddressId() {
		return addressId;
	}
	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}
	public Boolean isDefault() {
		return isDefault;
	}
	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}
	public Boolean isBilling() {
		return isBilling;
	}
	public void setIsBilling(Boolean isBilling) {
		this.isBilling = isBilling;
	}

}
