package com.pinguela.yourpc.model;

import java.util.Date;

public class TicketCriteria
extends AbstractCustomerOperationCriteria<Long, Ticket> {
	
	public static final String ORDER_BY_DATE = Ticket_.CREATION_DATE;
	
	private Date minDate;
	private Date maxDate;
	private String state;
	private String type;
	
	public TicketCriteria() {
	}
	@Override
	protected void setDefaultOrder() {
		orderBy(ORDER_BY_DATE, DESC);	
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
