package com.pinguela.yourpc.model;

import java.util.Date;

public class ProductStatisticsDTO 
extends AbstractValueObject {
	
	private Date date;
	private Integer quantitySold;
	private Integer quantityReturned;
	private Double avgPrice;
	
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
	public Double getAvgPrice() {
		return avgPrice;
	}
	public void setAvgPrice(Double avgPrice) {
		this.avgPrice = avgPrice;
	}
	
}
