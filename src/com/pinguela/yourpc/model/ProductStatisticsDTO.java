package com.pinguela.yourpc.model;

import java.util.Date;

public class ProductStatisticsDTO 
extends Product {
	
	private Date statisticsDate;
	private Integer quantitySold;
	private Integer quantityReturned;
	private Double pctReturned;
	private Double avgPurchasePrice;
	private Double avgSalePrice;
	
	public Date getStatisticsDate() {
		return statisticsDate;
	}
	public void setStatisticsDate(Date date) {
		this.statisticsDate = date;
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
