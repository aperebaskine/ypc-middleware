package com.pinguela.yourpc.model;

public class AttributeValue<E> extends AbstractValueObject implements Cloneable {

	private Long id = null;
	private E value = null;

	AttributeValue() {
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

}
