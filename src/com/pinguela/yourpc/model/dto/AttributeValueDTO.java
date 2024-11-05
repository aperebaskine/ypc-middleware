package com.pinguela.yourpc.model.dto;

import java.util.Objects;

public class AttributeValueDTO<T> 
extends AbstractDTO<Long> {
	
	private T value;
	
	AttributeValueDTO() {
	}

	AttributeValueDTO(Long id, T value) {
		super(id);
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	protected AttributeValueDTO<T> clone() {
		return new AttributeValueDTO<T>(getId(), this.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), value);
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
		return Objects.equals(getId(), other.getId()) && Objects.equals(value, other.value);
	}
	
	

}
