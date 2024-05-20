package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Product 
extends AbstractValueObject {

	private Long id;
	private String name;
	private String category;
	private Short categoryId;
	private String description;
	private Date launchDate;
	private Integer stock;
	private Double purchasePrice;
	private Double salePrice;
	private String replacementName;
	private Long replacementId;

	private Map<String, Attribute<?>> attributes = null;

	public Product() {
		this.attributes = new HashMap<String, Attribute<?>>();
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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

	public void setSalePrice(Double price) {
		this.salePrice = price;
	}

	public String getReplacementName() {
		return replacementName;
	}

	public void setReplacementName(String replacementName) {
		this.replacementName = replacementName;
	}

	public Long getReplacementId() {
		return replacementId;
	}

	public void setReplacementId(Long replacementId) {
		this.replacementId = replacementId;
	}

	public Short getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Short categoryId) {
		this.categoryId = categoryId;
	}

	public Map<String, Attribute<?>> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, Attribute<?>> attributes) {
		this.attributes = attributes;
	}
	
	public void addAttribute(Attribute<?> attribute) {
		this.attributes.put(attribute.getName(), attribute);
	}
	
}

