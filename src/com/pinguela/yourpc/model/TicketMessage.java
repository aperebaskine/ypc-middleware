package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class TicketMessage
extends AbstractValueObject {
	
	private @Id Long id;

	@ManyToOne
	@JoinColumn(name = "TICKET_ID")
	private Ticket ticket;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	private @CreationTimestamp @Column(name = "DATE") Date timestamp;
	
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
