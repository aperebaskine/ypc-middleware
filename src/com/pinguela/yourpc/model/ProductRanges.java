package com.pinguela.yourpc.model;

import java.util.Date;

public class ProductRanges extends AbstractValueObject {
	
	private Integer stockMin;
	private Integer stockMax;
	private Double priceMin;
	private Double priceMax;
	private Date launchDateMin;
	private Date launchDateMax;
	
	public Integer getStockMin() {
		return stockMin;
	}
	
	public void setStockMin(Integer stockMin) {
		this.stockMin = stockMin;
	}
	
	public Integer getStockMax() {
		return stockMax;
	}
	
	public void setStockMax(Integer stockMax) {
		this.stockMax = stockMax;
	}
	
	public Double getPriceMin() {
		return priceMin;
	}
	
	public void setPriceMin(Double priceMin) {
		this.priceMin = priceMin;
	}
	
	public Double getPriceMax() {
		return priceMax;
	}
	
	public void setPriceMax(Double priceMax) {
		this.priceMax = priceMax;
	}

	public Date getLaunchDateMin() {
		return launchDateMin;
	}

	public void setLaunchDateMin(Date launchDateMin) {
		this.launchDateMin = launchDateMin;
	}

	public Date getLaunchDateMax() {
		return launchDateMax;
	}

	public void setLaunchDateMax(Date launchDateMax) {
		this.launchDateMax = launchDateMax;
	}
	
}
