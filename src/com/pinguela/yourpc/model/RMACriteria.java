package com.pinguela.yourpc.model;

import java.util.Date;

public class RMACriteria
extends AbstractCustomerOperationCriteria<Long, RMA> {
	
	public static final String ORDER_BY_DATE = " r.CREATION_DATE";
	
	private Long orderId;
	private Long ticketId;
	private Date minDate;
	private Date maxDate;
	private String state;
	
	public RMACriteria() {	
	}
	
	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_DATE);	
		setAscDesc(DESC);
	}
	
	public Long getOrderId() {
		return orderId;
	}
	
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	
	public Long getTicketId() {
		return ticketId;
	}
	
	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
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

}
