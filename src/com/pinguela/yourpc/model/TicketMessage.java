package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
@Check(constraints = "(CUSTOMER_ID IS NULL AND ADDRESS_ID IS NOT NULL)"
		+ "OR (CUSTOMER_ID IS NOT NULL AND ADDRESS_ID IS NULL")
public class TicketMessage
extends AbstractEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	private Ticket ticket;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Employee employee;
	
	@CreationTimestamp
	@Column(name = "DATE") 
	private Date timestamp;
	
	private String text;
	
	public TicketMessage() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
