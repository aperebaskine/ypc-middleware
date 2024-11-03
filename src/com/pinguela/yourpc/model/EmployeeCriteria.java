package com.pinguela.yourpc.model;

public class EmployeeCriteria 
extends AbstractPersonCriteria<Integer, Employee> {
	
	private String username;
	private String departmentId;
	
	public EmployeeCriteria() {
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
