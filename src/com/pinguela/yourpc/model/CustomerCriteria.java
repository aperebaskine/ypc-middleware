package com.pinguela.yourpc.model;

public class CustomerCriteria
extends AbstractPersonCriteria<Integer, Customer> {
	
	public CustomerCriteria() {
	}
	
	@Override
	protected void setDefaultOrder() {
		orderBy(ORDER_BY_LAST_NAME, ASC);
	}
	
}
