package com.pinguela.yourpc.model;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.BOOLEAN)
public final class BooleanAttribute 
extends Attribute<Boolean> {
	
	BooleanAttribute() {
		super();
	}

	@Override
	public String getDataTypeIdentifier() {
		return BOOLEAN;
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

	@Override
	public Class<Boolean> getTypeParameterClass() {
		return java.lang.Boolean.class;
	}

}
