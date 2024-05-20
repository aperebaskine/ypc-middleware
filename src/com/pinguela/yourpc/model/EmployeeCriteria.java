package com.pinguela.yourpc.model;

public class EmployeeCriteria 
extends AbstractPersonCriteria<Integer, Employee> {
	
	public static final String ORDER_BY_FIRST_NAME = " e.FIRST_NAME";
	public static final String ORDER_BY_LAST_NAME = " e.LAST_NAME1";
	public static final String ORDER_BY_DOCUMENT_NUMBER = " e.DOCUMENT_NUMBER";
	public static final String ORDER_BY_PHONE_NUMBER = " e.PHONE";
	public static final String ORDER_BY_EMAIL = " e.EMAIL";
	public static final String ORDER_BY_USERNAME = " e.USERNAME";
	
	private String username;
	private String departmentId;
	
	public EmployeeCriteria() {
	}
	
	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_USERNAME);
		setAscDesc(ASC);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

}
