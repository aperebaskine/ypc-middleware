package com.pinguela.yourpc.model;

import java.util.Objects;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Immutable
@Table(name = "DOCUMENT_TYPE")
public class IDType 
extends AbstractValueObject {
	
	@Id
	@Column(columnDefinition = "CHAR(3)")
	private String id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	public IDType() {
	}
	
	public IDType(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IDType other = (IDType) obj;
		return Objects.equals(id, other.id);
	}
	
}
