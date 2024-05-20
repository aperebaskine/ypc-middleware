package com.pinguela.yourpc.model;

public class CustomerCriteria
extends AbstractPersonCriteria<Integer, Customer> {
	
	public static final String ORDER_BY_FIRST_NAME = " cu.FIRST_NAME";
	public static final String ORDER_BY_LAST_NAME = " cu.LAST_NAME1";
	public static final String ORDER_BY_DOCUMENT_NUMBER = " cu.DOCUMENT_NUMBER";
	public static final String ORDER_BY_PHONE_NUMBER = " cu.PHONE";
	public static final String ORDER_BY_EMAIL = " cu.EMAIL";
	
	public CustomerCriteria() {
	}
	
	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_LAST_NAME);
		setAscDesc(ASC);
	}
	
}
