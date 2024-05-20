package com.pinguela.yourpc.model;

import java.util.Date;

public class TicketCriteria
extends AbstractCustomerOperationCriteria<Long, Ticket> {
	
	public static final String ORDER_BY_DATE = " t.CREATION_DATE";
	
	private Date minDate;
	private Date maxDate;
	private String state;
	private String type;
	
	public TicketCriteria() {
	}
	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_DATE);
		setAscDesc(DESC);	
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
