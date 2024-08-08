package com.pinguela.yourpc.model;

public abstract class AbstractCriteria<T>
extends AbstractValueObject
implements Criteria<T> { 
	
	private String orderBy;
	private Boolean ascDesc;
	
	{
		setDefaultOrdering();
	}
	
	@Override
	public String getOrderBy() {
		return orderBy;
	}
	
	@Override
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	@Override
	public Boolean getAscDesc() {
		return ascDesc;
	}
	
	@Override
	public void setAscDesc(Boolean ascDesc) {
		this.ascDesc = ascDesc;
	}
	
	/**
	 * <b>This method is called during object initialisation.</b>
	 * <p>
	 * Sets the default column name that the query results are being ordered by, as well as an indicator
	 * determining whether the results are displayed in ascending or descending order.
	 * </p>
	 * <p>
	 * Subclasses must provide constants for the name of each column that may be subject to ordering, as well
	 * as assign one of them as the default one using the {@link #setOrderBy(String)} method.
	 * <br>
	 * Ascending or descending ordering must be set using the {@link #setAscDesc(Boolean)} method, and
	 * using one of the two constants {@link #ASC} or {@link #DESC} provided by the superclass.
	 * </p>
	 */
	protected abstract void setDefaultOrdering();

}
