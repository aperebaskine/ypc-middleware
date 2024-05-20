package com.pinguela.yourpc.model;

import java.util.Date;

public class CustomerOrderCriteria 
extends AbstractCustomerOperationCriteria<Long, CustomerOrder> {
	
	public static final String ORDER_BY_AMOUNT = " co.TOTAL_PRICE";
	public static final String ORDER_BY_DATE = " co.ORDER_DATE";
	
	private Double minAmount;
	private Double maxAmount;
	private Date minDate;
	private Date maxDate;
	private String state;
	
	public CustomerOrderCriteria() {
	}
	
	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_DATE);
		setAscDesc(DESC);
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
