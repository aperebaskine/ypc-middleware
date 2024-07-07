package com.pinguela.yourpc.model;

import java.util.Date;

public class ProductStatistics 
extends AbstractValueObject {
	
	private Long productId;
	private String productName;
	private Date date;
	private Integer quantitySold;
	private Integer quantityReturned;
	private Double pctReturned;
	private Double avgPurchasePrice;
	private Double avgSalePrice;
	
	
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long id) {
		this.productId = id;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String name) {
		this.productName = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getQuantitySold() {
		return quantitySold;
	}
	public void setQuantitySold(Integer quantity) {
		this.quantitySold = quantity;
	}
	public Integer getQuantityReturned() {
		return quantityReturned;
	}
	public void setQuantityReturned(Integer quantityReturned) {
		this.quantityReturned = quantityReturned;
	}
	public Double getPctReturned() {
		return pctReturned;
	}
	public void setPctReturned(Double pctReturned) {
		this.pctReturned = pctReturned;
	}
	public Double getAvgPurchasePrice() {
		return avgPurchasePrice;
	}
	public void setAvgPurchasePrice(Double avgPurchasePrice) {
		this.avgPurchasePrice = avgPurchasePrice;
	}
	public Double getAvgSalePrice() {
		return avgSalePrice;
	}
	public void setAvgSalePrice(Double avgPrice) {
		this.avgSalePrice = avgPrice;
	}
	
}
