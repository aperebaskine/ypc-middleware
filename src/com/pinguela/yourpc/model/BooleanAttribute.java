package com.pinguela.yourpc.model;

import com.pinguela.yourpc.constants.AttributeDataTypes;
import com.pinguela.yourpc.constants.AttributeValueHandlingModes;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.BOOLEAN)
public final class BooleanAttribute 
extends Attribute<Boolean> {
	
	BooleanAttribute() {
		super();
	}

	@Override
	public String getDataTypeIdentifier() {
		return AttributeDataTypes.BOOLEAN;
	}

	@Override
	public int getValueHandlingMode() {
		return AttributeValueHandlingModes.SET;
	}

	@Override
	public Class<Boolean> getTypeParameterClass() {
		return java.lang.Boolean.class;
	}

}
