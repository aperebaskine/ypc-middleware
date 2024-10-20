package com.pinguela.yourpc.model;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractCriteria<T> 
extends AbstractValueObject { 
		
	public static final Boolean ASC = Boolean.TRUE;
	public static final Boolean DESC = Boolean.FALSE;
	
	private Map<String, Boolean> orderBy;
	
	public AbstractCriteria() {
		orderBy = new LinkedHashMap<>();
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
		this.orderBy.put(orderBy, ascDesc);
	}

}
