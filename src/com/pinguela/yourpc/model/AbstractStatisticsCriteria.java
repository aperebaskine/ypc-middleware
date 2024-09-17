package com.pinguela.yourpc.model;

import java.util.Date;

@SuppressWarnings("serial")
public abstract class AbstractStatisticsCriteria<T> 
extends AbstractCriteria<T> {
	
	private Date startDate;
	private Date endDate;
	
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

}
