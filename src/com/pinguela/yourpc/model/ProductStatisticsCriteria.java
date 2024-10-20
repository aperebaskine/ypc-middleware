package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.Map;

public class ProductStatisticsCriteria 
extends AbstractStatisticsCriteria<ProductStatistics> {
	
	private ProductCriteria productCriteria;
	
	public ProductStatisticsCriteria() {
		this.productCriteria = new ProductCriteria();
	}

	public Long getProductId() {
	    return productCriteria.getId();
	}

	public void setProductId(Long id) {
	    productCriteria.setId(id);
	}

	public String getProductName() {
	    return productCriteria.getName();
	}

	public void setProductName(String name) {
	    productCriteria.setName(name);
	}

	public Date getProductLaunchDateMin() {
	    return productCriteria.getLaunchDateMin();
	}

	public void setProductLaunchDateMin(Date launchDateMin) {
	    productCriteria.setLaunchDateMin(launchDateMin);
	}

	public Date getProductLaunchDateMax() {
	    return productCriteria.getLaunchDateMax();
	}

	public void setProductLaunchDateMax(Date launchDateMax) {
	    productCriteria.setLaunchDateMax(launchDateMax);
	}

	public Integer getProductStockMin() {
	    return productCriteria.getStockMin();
	}

	public void setProductStockMin(Integer stockMin) {
	    productCriteria.setStockMin(stockMin);
	}

	public Integer getProductStockMax() {
	    return productCriteria.getStockMax();
	}

	public void setProductStockMax(Integer stockMax) {
	    productCriteria.setStockMax(stockMax);
	}

	public Double getProductPriceMin() {
	    return productCriteria.getPriceMin();
	}

	public void setProductPriceMin(Double priceMin) {
	    productCriteria.setPriceMin(priceMin);
	}

	public Double getProductPriceMax() {
	    return productCriteria.getPriceMax();
	}

	public void setProductPriceMax(Double priceMax) {
	    productCriteria.setPriceMax(priceMax);
	}

	public Short getProductCategoryId() {
	    return productCriteria.getCategoryId();
	}

	public void setProductCategoryId(Short categoryId) {
	    productCriteria.setCategoryId(categoryId);
	}

	public Map<String, Attribute<?>> getProductAttributes() {
	    return productCriteria.getAttributes();
	}

	public void setProductAttributes(Map<String, Attribute<?>> attributes) {
	    productCriteria.setAttributes(attributes);
	}

}
