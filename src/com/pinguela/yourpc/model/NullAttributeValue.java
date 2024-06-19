package com.pinguela.yourpc.model;

public class NullAttributeValue 
extends AttributeValue<String> {
	
	public NullAttributeValue() {
		super.setId(0l);
	}

	@Override
	public void setId(Long id) {}

	@Override
	public void setValue(String value) {}

	@Override
	public AttributeValue<String> clone() {
		return this;
	}

}
