package com.pinguela.yourpc.model;

public abstract class AbstractCustomerOperationCriteria<PK, T> extends AbstractEntityCriteria<PK, T> {
	
	private Integer customerId;
	private String customerEmail;
	
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	
}
