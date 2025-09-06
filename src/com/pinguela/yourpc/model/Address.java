package com.pinguela.yourpc.model;

import java.util.Date;
import java.util.Objects;

public class Address
extends AbstractValueObject {
	
	private Integer id;
	private String name;
	private Integer customerId;
	private Integer employeeId;
	private String streetName;
	private Short streetNumber;
	private Short floor;
	private String door;
	private String zipCode;
	private Integer cityId;
	private String city;
	private Integer provinceId;
	private String province;
	private String countryId;
	private String country;
	private Boolean isDefault = false;
	private Boolean isBilling = false;
	private Date creationDate;
	
	public Address() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Boolean isDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	public Boolean isBilling() {
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

	@Override
	public int hashCode() {
		return Objects.hash(cityId, customerId, door, employeeId, floor, streetName, streetNumber, zipCode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		return Objects.equals(cityId, other.cityId) && Objects.equals(customerId, other.customerId)
				&& Objects.equals(door, other.door) && Objects.equals(employeeId, other.employeeId)
				&& Objects.equals(floor, other.floor) && Objects.equals(streetName, other.streetName)
				&& Objects.equals(streetNumber, other.streetNumber) && Objects.equals(zipCode, other.zipCode);
	}
	
}
