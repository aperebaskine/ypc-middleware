package com.pinguela.yourpc.model;

import java.util.Objects;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Id;

@Embeddable
public class ItemType<T> extends AbstractValueObject {

	private @Id String id;
	private @NaturalId String name;

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
		ItemType<T> other = (ItemType<T>) obj;
		return Objects.equals(id, other.id);
	}

}
