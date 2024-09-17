package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class EmployeeDepartment extends AbstractValueObject {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	@Id
	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;
	
	@Id
	@Column(columnDefinition = "DATE")
	private Date startDate;
	
	@Column(columnDefinition = "DATE")
	private Date endDate;
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
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
	@Override
	public int hashCode() {
		return Objects.hash(department, employee, endDate);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmployeeDepartment other = (EmployeeDepartment) obj;
		return Objects.equals(department, other.department) && Objects.equals(employee, other.employee)
				&& Objects.equals(endDate, other.endDate);
	}

}
