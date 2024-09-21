package com.pinguela.yourpc.model;

import java.util.Objects;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Immutable
@MappedSuperclass
public class EntityState<T> extends AbstractValueObject {
	
	@Id
	@Column(columnDefinition = "CHAR(3)") 
	private String id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
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
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntityState<T> other = (EntityState<T>) obj;
		return Objects.equals(id, other.id);
	}
	
}
