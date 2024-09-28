package com.pinguela.yourpc.model;

public class AddressCriteria 
extends AbstractEntityCriteria<Integer, Address> {
	
	private Integer customerId;
	private Integer employeeId;
	
	public AddressCriteria() {
	}

	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	@Override
	protected void setDefaultOrder() {
		orderBy(ORDER_BY_ID, ASC);
	}
	
}
	