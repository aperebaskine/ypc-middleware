package com.pinguela.yourpc.model;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

@Entity
public class AttributeValue<E> 
extends AbstractValueObject 
implements Cloneable {

	private @Id Integer id = null;
	private @Transient E value = null; // Persistence to be handled by manual queries

	AttributeValue() {
	}
	
	AttributeValue(Integer id, E value) {
		this.id = id;
		this.value = value;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public E getValue() {
		return value;
	}

	public void setValue(E value) {
		this.value = value;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public AttributeValue<E> clone() {
		try {
			return (AttributeValue<E>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeValue<?> other = (AttributeValue<?>) obj;
		return Objects.equals(id, other.id) && Objects.equals(value, other.value);
	}
	
	

}
