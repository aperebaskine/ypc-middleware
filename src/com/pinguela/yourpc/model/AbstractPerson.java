package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class AbstractPerson
extends AbstractValueObject {

	private @Id Integer id;
	private @Embedded FullName name;
	private @Embedded ID document;
	private @Column(name = "PHONE") String phoneNumber;
	private String email;
	private @CreationTimestamp Date creationDate;
	private @Transient String unencryptedPassword;
	private @Column(name = "PASSWORD") String encryptedPassword;

	public AbstractPerson() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public FullName getName() {
		return name;
	}

	public void setName(FullName name) {
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

	public ID getDocument() {
		return document;
	}

	public void setDocument(ID document) {
		this.document = document;
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
