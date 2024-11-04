package com.pinguela.yourpc.model.dto;

import java.util.Objects;

import com.pinguela.yourpc.model.AttributeValue;

public class AttributeValueDTO<T> 
extends AbstractDTO<AttributeValue<T>> {
	
	private Long id;
	private T value;
	
	AttributeValueDTO() {
	}

	AttributeValueDTO(Long id, T value) {
		super();
		this.id = id;
		this.value = value;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	protected AttributeValueDTO<T> clone() {
		return new AttributeValueDTO<T>(this.id, this.value);
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
		AttributeValueDTO<?> other = (AttributeValueDTO<?>) obj;
		return Objects.equals(id, other.id) && Objects.equals(value, other.value);
	}
	
	

}
