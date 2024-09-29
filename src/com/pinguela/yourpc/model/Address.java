package com.pinguela.yourpc.model;

import java.util.Date;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SoftDelete;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
@Check(constraints = "(CUSTOMER_ID IS NULL AND EMPLOYEE_ID IS NOT NULL)"
		+ "OR (CUSTOMER_ID IS NOT NULL AND EMPLOYEE_ID IS NULL")
public class Address
extends AbstractEntity<Integer> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CUSTOMER_ID")
	private Customer customer;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPLOYEE_ID")
	private Employee employee;
	
	@Column(nullable = false)
	private String streetName;
	
	private Short streetNumber;
	
	private Short floor;
	
	private String door;
	
	@Column(nullable = false)
	private String zipCode;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "CITY_ID")
	private City city;
	
	private Boolean isDefault;
	private Boolean isBilling;
	
	@CreationTimestamp
	private Date creationDate;
	
	@SoftDelete
	private Date deletionDate;
	
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
	
	public Integer getCustomerId() {
		return customer == null ? null : customer.getId();
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	
	public Integer getEmployeeId() {
		return employee == null ? null : employee.getId();
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

	public Boolean isDefault() {
		return isDefault;
	}

	public void setDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean isBilling() {
		return isBilling;
	}

	public void setBilling(Boolean isBilling) {
		this.isBilling = isBilling;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getDeletionDate() {
		return deletionDate;
	}

	public void setDeletionDate(Date deletionDate) {
		this.deletionDate = deletionDate;
	}
	
}
