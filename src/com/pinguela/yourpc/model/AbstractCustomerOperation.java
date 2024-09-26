package com.pinguela.yourpc.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractCustomerOperation<PK extends Comparable<PK>> 
extends AbstractEntity<PK> {
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Integer getCustomerId() {
		return customer.getId();
	}

}
