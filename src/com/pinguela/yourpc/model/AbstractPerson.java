package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Transient;

@MappedSuperclass
public abstract class AbstractPerson
extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Embedded
	private FullName name;
	
	@Embedded
	private ID document;
	
	@Column(name = "PHONE", nullable = false) 
	private String phoneNumber;
	
	@NaturalId
	private String email;
	
	@CreationTimestamp
	private Date creationDate;
		
	@Transient
	private String unencryptedPassword;
	
	@Column(name = "PASSWORD", nullable = false) 
	private String encryptedPassword;

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
