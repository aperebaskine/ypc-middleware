package com.pinguela.yourpc.model;

public class EmployeeCriteria 
extends AbstractPersonCriteria<Integer, Employee> {
	
	public static final String ORDER_BY_USERNAME = Employee_.USERNAME;
	
	private String username;
	private String departmentId;
	
	public EmployeeCriteria() {
	}
	
	@Override
	protected void setDefaultOrder() {
		orderBy(ORDER_BY_USERNAME, ASC);
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
