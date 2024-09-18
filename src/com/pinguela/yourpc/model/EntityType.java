package com.pinguela.yourpc.model;

import java.util.Objects;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EntityType<T> extends AbstractValueObject {

	private @Id @Column(insertable = false, updatable = false, columnDefinition = "CHAR(3)") String id;
	private @NaturalId @Column(insertable = false, updatable = false) String name;

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
		EntityType<T> other = (EntityType<T>) obj;
		return Objects.equals(id, other.id);
	}

}
