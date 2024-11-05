package com.pinguela.yourpc.model.dto;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public abstract class AbstractProductDTO 
extends AbstractDTO<Long> {
	
	private Short categoryId;
	private Date launchDate;
	private Date discontinuationDate;
	private Integer stock;
	private Double purchasePrice;
	private Double salePrice;
	
	private Long replacementId;
	private String replacementName;

	private Map<String, AttributeDTO<?>> attributes;
	
	public AbstractProductDTO() {
		attributes = new TreeMap<String, AttributeDTO<?>>();
	}

	public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	public Date getDiscontinuationDate() {
		return discontinuationDate;
	}

	public void setDiscontinuationDate(Date discontinuationDate) {
		this.discontinuationDate = discontinuationDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
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

	public Long getReplacementId() {
		return replacementId;
	}

	public void setReplacementId(Long replacementId) {
		this.replacementId = replacementId;
	}

	public String getReplacementName() {
		return replacementName;
	}

	public void setReplacementName(String replacementName) {
		this.replacementName = replacementName;
	}

	public Map<String, AttributeDTO<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, AttributeDTO<?>> attributes) {
		this.attributes = attributes;
	}

}
