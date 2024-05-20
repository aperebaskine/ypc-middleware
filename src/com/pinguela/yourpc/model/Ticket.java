package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Ticket 
extends AbstractValueObject {
	
	private Long id;
	private Integer customerId;
	private Integer employeeId;
	private Date creationDate;
	private String state;
	private String type;
	private Long productId;
	private String title;
	private String description;
	
	private List<OrderLine> orderLines = null;
	private List<TicketMessage> messageList = null;
	
	public Ticket() {
		orderLines = new ArrayList<OrderLine>();
		messageList = new ArrayList<TicketMessage>();
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
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
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
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public List<TicketMessage> getMessageList() {
		return messageList;
	}
	public void setMessageList(List<TicketMessage> messageList) {
		this.messageList = messageList;
	}
	public List<OrderLine> getOrderLines() {
		return orderLines;
	}
	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	} 
}
