package com.pinguela.yourpc.model;

import javax.lang.model.type.NullType;

public class NullAttributeValue 
extends AttributeValue<NullType> {
	
	public NullAttributeValue() {
		super.setId(0l);
	}

	@Override
	public void setId(Long id) {}

	@Override
	public void setValue(NullType value) {}

	@Override
	public AttributeValue<NullType> clone() {
		return this;
	}

}
