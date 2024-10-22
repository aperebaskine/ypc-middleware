package com.pinguela.yourpc.model.dto;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Product;

public class ProductDTO 
extends AbstractDTO<Product> {
	
	private Long id;
	private String name;
	private Short categoryId;
	private String description;
	private Date launchDate;
	private Date discontinuationDate;
	private Integer stock;
	private Double purchasePrice;
	private Double salePrice;
	
	private Long replacementId;
	private String replacementName;

	private Map<String, Attribute<?>> attributes;
	
	public ProductDTO() {
		attributes = new LinkedHashMap<String, Attribute<?>>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Map<String, Attribute<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}

}
