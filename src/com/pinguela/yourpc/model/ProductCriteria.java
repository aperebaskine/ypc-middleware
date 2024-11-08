package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.pinguela.yourpc.model.dto.AttributeDTO;

public class ProductCriteria
extends AbstractEntityCriteria<Long, Product> {
	
	private String name = null;
	private Date launchDateMin = null;
	private Date launchDateMax = null;
	private Integer stockMin = null;
	private Integer stockMax = null;
	private Double priceMin = null;
	private Double priceMax = null;
	private Short categoryId = null;
	private Map<String, AttributeDTO<?>> attributes;
	
	public ProductCriteria() {
		attributes = new TreeMap<String, AttributeDTO<?>>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public Map<String, AttributeDTO<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, AttributeDTO<?>> attributes) {
		this.attributes = attributes;
	}

}
