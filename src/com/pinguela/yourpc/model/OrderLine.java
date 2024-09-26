package com.pinguela.yourpc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class OrderLine 
extends AbstractEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "CUSTOMER_ORDER_ID")
	private CustomerOrder order;

	@ManyToOne(optional = false)
	@JoinColumn(name = "PRODUCT_ID")
	private Product product;

	@Column(nullable = false)
	private Short quantity;

	@Column(columnDefinition = "DECIMAL(20,8)", nullable = false) 
	private Double purchasePrice;

	@Column(columnDefinition = "DECIMAL(20,8)", nullable = false) 
	private Double salePrice;

	public OrderLine() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerOrder getOrder() {
		return order;
	}
	
	public Long getOrderId() {
		return order.getId();
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Short getQuantity() {
		return quantity;
	}

	public void setQuantity(Short quantity) {
		this.quantity = quantity;
	}

	public Double getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(Double purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

}
