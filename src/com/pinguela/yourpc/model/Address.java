package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Address
extends AbstractValueObject {
	
	private @Id Integer id;
	
	@ManyToOne
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	private String streetName;
	private Short streetNumber;
	private Short floor;
	private String door;
	private String zipCode;

	@ManyToOne
	@JoinColumn(name = "CITY_ID")
	private City city;
	
	private Boolean isDefault;
	private Boolean isBilling;
	private @CreationTimestamp Date creationDate;
	
	public Address() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public Short getStreetNumber() {
		return streetNumber;
	}

	public void setStreetNumber(Short streetNumber) {
		this.streetNumber = streetNumber;
	}

	public Short getFloor() {
		return floor;
	}

	public void setFloor(Short floor) {
		this.floor = floor;
	}

	public String getDoor() {
		return door;
	}

	public void setDoor(String door) {
		this.door = door;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean getIsBilling() {
		return isBilling;
	}

	public void setIsBilling(Boolean isBilling) {
		this.isBilling = isBilling;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
}
