package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.List;

import org.hibernate.annotations.SoftDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Employee
extends AbstractPerson {
	
	private @Column(unique = true) String username;
	private @SoftDelete Date terminationDate;
	private String iban;
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

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
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

	public List<EmployeeDepartment> getDepartmentHistory() {
		return departmentHistory;
	}

	public void setDepartmentHistory(List<EmployeeDepartment> departmentHistory) {
		this.departmentHistory = departmentHistory;
	}
	
}
