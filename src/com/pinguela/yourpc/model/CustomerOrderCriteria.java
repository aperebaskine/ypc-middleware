package com.pinguela.yourpc.model;

import java.util.Date;

public class CustomerOrderCriteria 
extends AbstractCustomerOperationCriteria<Long, CustomerOrder> {
	
	private Double minAmount;
	private Double maxAmount;
	private Date minDate;
	private Date maxDate;
	private String state;
	
	public CustomerOrderCriteria() {
	}
	
	public Double getMinAmount() {
		return minAmount;
	}
	
	public void setMinAmount(Double minAmount) {
		this.minAmount = minAmount;
	}
	
	public Double getMaxAmount() {
		return maxAmount;
	}
	
	public void setMaxAmount(Double maxAmount) {
		this.maxAmount = maxAmount;
	}
	
	public Date getMinDate() {
		return minDate;
	}
	
	public void setMinDate(Date dateMin) {
		this.minDate = dateMin;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(Date dateMax) {
		this.maxDate = dateMax;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}

}
