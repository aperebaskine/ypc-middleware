package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;

@Entity
public class Product 
extends AbstractValueObject {

	private @Id Long id;
	private String name;

	private @ManyToOne Category category;
	private String description;
	private Date launchDate;
	private Integer stock;
	private @Column(columnDefinition = "DECIMAL(8,20)") Double purchasePrice;
	private @Column(columnDefinition = "DECIMAL(8,20)") Double salePrice;

	@ManyToOne
	@JoinColumn(name = "REPLACEMENT_ID")
	private Product replacement;

	@ManyToMany
	@MapKey(name = "name")
	@JoinTable(name = "PRODUCT_ATTRIBUTE_VALUE", inverseJoinColumns = @JoinColumn(name = "ATTRIBUTE_VALUE_ID"))
	private Map<String, Attribute<?>> attributes;

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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
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

	public Product getReplacement() {
		return replacement;
	}

	public void setReplacement(Product replacement) {
		this.replacement = replacement;
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

