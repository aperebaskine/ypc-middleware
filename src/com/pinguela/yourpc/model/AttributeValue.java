package com.pinguela.yourpc.model;

import java.util.Objects;

public class AttributeValue<E> 
extends AbstractValueObject 
implements Cloneable {

	private Long id = null;
	private E value = null;

	AttributeValue() {
	}
	
	AttributeValue(Long id, E value) {
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
