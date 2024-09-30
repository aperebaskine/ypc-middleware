package com.pinguela.yourpc.model;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractCriteria<T> 
extends AbstractValueObject { 
		
	public static final Boolean ASC = Boolean.TRUE;
	public static final Boolean DESC = Boolean.FALSE;
	
	private Map<String, Boolean> orderBy;
	private boolean isDefaultOrderSet;
	
	public AbstractCriteria() {
		orderBy = new LinkedHashMap<>();
		setDefaultOrder();
		isDefaultOrderSet = true;
	}
	
	public Map<String, Boolean> getOrderBy() {
		return orderBy;
	}

	/**
	 * Add a column to the order by clause.
	 * Clears the default order the first time it is called.
	 * @param orderBy 
	 * @param ascDesc
	 */
	public void orderBy(String orderBy, boolean ascDesc) {
		if (isDefaultOrderSet) {
			this.orderBy.clear();
			isDefaultOrderSet = false;
		}
		this.orderBy.put(orderBy, ascDesc);
	}

	/**
	 * <b>This method is called during object initialisation.</b>
	 * <p>
	 * Sets the default column name(s) that the query results are being ordered by, as well as an indicator
	 * determining whether the results are displayed in ascending or descending order for each column.
	 * </p>
	 * <p>
	 * Subclasses must provide constants for the name of each column that may be subject to ordering, as well
	 * as assign one of them as the default one using the {@link #orderBy(String, boolean)} method.
	 * <br>
	 * Ascending or descending ordering must be set using the {@link #setAscDesc(Boolean)} method, and
	 * using one of the two constants {@link #ASC} or {@link #DESC} provided by the superclass.
	 * </p>
	 */
	protected abstract void setDefaultOrder();

}
