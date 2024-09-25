package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Ticket 
extends AbstractEntity<Long> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Customer customer;
	
	@ManyToOne
	private Employee employee;
	
	@CreationTimestamp
	@Column(nullable = false)
	private Date creationDate;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "TICKET_STATE_ID")
	private TicketState state;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "TICKET_TYPE_ID")
	private TicketType type;
	
	@ManyToOne
	private Product product;

	@Column(nullable = false)
	private String title;
	
	private String description;
	
	@ManyToMany
	@JoinTable(name = "TICKET_ORDER_LINE", inverseJoinColumns = @JoinColumn(name = "ORDER_LINE_ID"))
	private List<OrderLine> orderLines = null;
	
	@OneToMany(mappedBy = "ticket")
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public TicketState getState() {
		return state;
	}

	public void setState(TicketState state) {
		this.state = state;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

	public List<TicketMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<TicketMessage> messageList) {
		this.messageList = messageList;
	}
	
}
