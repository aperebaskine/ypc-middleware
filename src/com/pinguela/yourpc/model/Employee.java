package com.pinguela.yourpc.model;

import java.util.Date;

public class Employee
extends AbstractPerson {
	
	private String username;
	private Date terminationDate;
	private String iban;
	private String bic;
	private Integer supervisorId;
	private String supervisorFirstName;
	private String supervisorLastName1;
	private String supervisorLastName2;
	
	private Address address;
	
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

	public Integer getSupervisorId() {
		return supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
	}

	public String getSupervisorFirstName() {
		return supervisorFirstName;
	}

	public void setSupervisorFirstName(String supervisorFirstName) {
		this.supervisorFirstName = supervisorFirstName;
	}

	public String getSupervisorLastName1() {
		return supervisorLastName1;
	}

	public void setSupervisorLastName1(String supervisorLastName1) {
		this.supervisorLastName1 = supervisorLastName1;
	}

	public String getSupervisorLastName2() {
		return supervisorLastName2;
	}

	public void setSupervisorLastName2(String supervisorLastName2) {
		this.supervisorLastName2 = supervisorLastName2;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
