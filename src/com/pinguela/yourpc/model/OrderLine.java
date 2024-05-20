package com.pinguela.yourpc.model;

public class OrderLine 
extends AbstractValueObject {
	
	private Long id;
	private Long customerOrderId;
	private Integer productId;
	private Short quantity;
	private Double purchasePrice;
	private Double salePrice;
	
	public OrderLine() {
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getCustomerOrderId() {
		return customerOrderId;
	}
	
	public void setCustomerOrderId(Long purchaseOrderId) {
		this.customerOrderId = purchaseOrderId;
	}
	
	public Integer getProductId() {
		return productId;
	}
	
	public void setProductId(Integer productId) {
		this.productId = productId;
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
