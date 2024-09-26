package com.pinguela.yourpc.model;

import java.util.List;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@AttributeOverride(name = "deletionDate", column = @Column(name = "TERMINATION_DATE"))
public class Employee
extends AbstractPerson {
	
	@NaturalId
	private String username;
	
	@Column(nullable = false)
	private String iban;
	
	@Column(nullable = false)
	private String bic;

	@ManyToOne
	@JoinColumn(name = "SUPERVISOR_ID")
	private Employee supervisor;
	
	@OneToOne(mappedBy = "employee")
	private Address address;
	
	@OneToMany(mappedBy = "employee")
	private List<EmployeeDepartment> departmentHistory;
	
	public Employee() {
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getBic() {
		return bic;
	}

	public void setBic(String bic) {
		this.bic = bic;
	}

	public Employee getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Employee supervisor) {
		this.supervisor = supervisor;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
	
	public Integer getAddressId() {
		return address.getId();
	}

	public List<EmployeeDepartment> getDepartmentHistory() {
		return departmentHistory;
	}

	public void setDepartmentHistory(List<EmployeeDepartment> departmentHistory) {
		this.departmentHistory = departmentHistory;
	}
	
}
