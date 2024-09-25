package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

@Entity
public class RMA 
extends AbstractEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Customer customer;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "RMA_STATE_ID")
	private RMAState state;

	@CreationTimestamp 
	private Date creationDate;
	
	private String trackingNumber;
	
	@ManyToMany
	@JoinTable(name = "RMA_ORDER_LINE", inverseJoinColumns = @JoinColumn(name = "ORDER_LINE_ID"))
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
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public RMAState getState() {
		return state;
	}
	public void setState(RMAState state) {
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
