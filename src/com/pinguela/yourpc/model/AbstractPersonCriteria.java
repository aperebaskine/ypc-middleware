package com.pinguela.yourpc.model;

public abstract class AbstractPersonCriteria<PK, T> 
extends AbstractEntityCriteria<PK, T> {
	
	public static final String ORDER_BY_FIRST_NAME = compositePath(AbstractPerson_.NAME, FullName_.FIRST_NAME);
	public static final String ORDER_BY_LAST_NAME = compositePath(AbstractPerson_.NAME, FullName_.LAST_NAME1);
	public static final String ORDER_BY_DOCUMENT_NUMBER = compositePath(AbstractPerson_.NAME, FullName_.LAST_NAME2);
	public static final String ORDER_BY_PHONE_NUMBER = AbstractPerson_.PHONE_NUMBER;
	public static final String ORDER_BY_EMAIL = AbstractPerson_.EMAIL;
	
	private String firstName = null;
	private String lastName1 = null;
	private String lastName2 = null;
	private String documentNumber = null;
	private String phoneNumber = null;
	private String email = null;
	
	public AbstractPersonCriteria() {
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

	public String getDocumentNumber() {
		return documentNumber;
	}
	
	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
}
