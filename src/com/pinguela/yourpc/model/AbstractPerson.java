package com.pinguela.yourpc.model;

import java.util.Date;

public abstract class AbstractPerson
extends AbstractValueObject {

	private Integer id;
	private FullName name;
	private String documentTypeId;
	private String documentType;
	private String documentNumber;
	private String phoneNumber;
	private String email;
	private Date creationDate;
	private String unencryptedPassword;
	private String encryptedPassword;

	public AbstractPerson() {
		name = new FullName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FullName getFullName() {
		return name;
	}

	public void setFullName(FullName name) {
		this.name = name;
	}

	public String getFirstName() {
		return name.getFirstName();
	}

	public void setFirstName(String firstName) {
		name.setFirstName(firstName);
	}

	public String getLastName1() {
		return name.getLastName1();
	}

	public void setLastName1(String lastName1) {
		name.setLastName1(lastName1);
	}

	public String getLastName2() {
		return name.getLastName2();
	}

	public void setLastName2(String lastName2) {
		name.setLastName2(lastName2);
	}

	public String getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(String documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

	public String getDocumentType() {
		return documentType;
	}

	public void setDocumentType(String documentType) {
		this.documentType = documentType;
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

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getUnencryptedPassword() {
		return unencryptedPassword;
	}

	public void setUnencryptedPassword(String unencryptedPassword) {
		this.unencryptedPassword = unencryptedPassword;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

}
