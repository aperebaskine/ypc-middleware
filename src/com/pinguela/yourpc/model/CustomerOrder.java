package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class CustomerOrder 
extends AbstractValueObject {
	
	private @Id Long id;
	
	@ManyToOne
	@JoinTable(name = "ORDER_STATE", joinColumns = @JoinColumn(name = "CUSTOMER_ORDER_ID"))
	private ItemState<CustomerOrder> state;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;

	private @CreationTimestamp Date orderDate;
	private String trackingNumber;

	@ManyToOne
	@JoinColumn(name = "BILLING_ADDRESS_ID")
	private Address billingAddress;
	
	@ManyToOne
	@JoinColumn(name = "SHIPPING_ADDRESS_ID")
	private Address shippingAddress;
	
	private @Column(name = "INVOICE_TOTAL", columnDefinition = "DECIMAL(20,8)") Double totalPrice;
	
	@OneToMany(mappedBy = "order")
	private List<OrderLine> orderLines;
	
	public CustomerOrder() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ItemState<CustomerOrder> getState() {
		return state;
	}

	public void setState(ItemState<CustomerOrder> state) {
		this.state = state;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Address getBillingAddress() {
		return billingAddress;
	}

	public void setBillingAddress(Address billingAddress) {
		this.billingAddress = billingAddress;
	}

	public Address getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(Address shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public List<OrderLine> getOrderLines() {
		return orderLines;
	}

	public void setOrderLines(List<OrderLine> orderLines) {
		this.orderLines = orderLines;
	}

}
