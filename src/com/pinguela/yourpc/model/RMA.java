package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RMA 
extends AbstractValueObject {
	
	private Long id;
	private Integer customerId;
	private String state;
	private Date creationDate;
	private String trackingNumber;
	
	private List<OrderLine> orderLines;
	
	public RMA() {
		orderLines = new ArrayList<OrderLine>();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getTrackingNumber() {
		return trackingNumber;
	}
	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

}
