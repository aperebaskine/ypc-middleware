package com.pinguela.yourpc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FullName 
extends AbstractValueObject {
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName1;
	
	private String lastName2;
	
	public FullName() {
	}
	
	public FullName(String firstName, String lastName1, String lastName2) {
		this.firstName = firstName;
		this.lastName1 = lastName1;
		this.lastName2 = lastName2;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName1() {
		return lastName1;
	}
	public void setLastName1(String lastName1) {
		this.lastName1 = lastName1;
	}
	public String getLastName2() {
		return lastName2;
	}
	public void setLastName2(String lastName2) {
		this.lastName2 = lastName2;
	}

}
