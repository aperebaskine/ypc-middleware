package com.pinguela.yourpc.model;

import com.pinguela.yourpc.constants.AttributeDataTypes;
import com.pinguela.yourpc.constants.AttributeValueHandlingModes;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.BIGINT)
public final class LongAttribute extends Attribute<Long> {

	LongAttribute() {
		super();
	}
	
	@Override
	public String getDataTypeIdentifier() {
		return AttributeDataTypes.BIGINT;
	}
	
	@Override
	public int getValueHandlingMode() {
		return AttributeValueHandlingModes.RANGE;
	}

	@Override
	public Class<Long> getTypeParameterClass() {
		return java.lang.Long.class;
	}
}
