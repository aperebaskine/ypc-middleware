package com.pinguela.yourpc.model;

import java.util.Date;

public class TicketCriteria
extends AbstractCustomerOperationCriteria<Long, Ticket> {
	
	private Date minDate;
	private Date maxDate;
	private String state;
	private String type;
	
	public TicketCriteria() {
	}
	
	public Date getMinDate() {
		return minDate;
	}
	
	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}
	
	public Date getMaxDate() {
		return maxDate;
	}
	
	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

}
