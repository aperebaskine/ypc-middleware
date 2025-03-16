package com.pinguela.yourpc.model;

import java.util.Date;

public class ProductStatisticsCriteria
extends AbstractCriteria<Long, ProductStatistics> {
    private Short productCategoryId;
    private Date startDate;
    private Date endDate;

    public Short getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Short productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

	@Override
	protected void setDefaultOrdering() {
		
	}
}
