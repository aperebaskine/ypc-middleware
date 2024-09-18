package com.pinguela.yourpc.model;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.BIGINT)
public final class LongAttribute extends Attribute<Long> {

	LongAttribute() {
		super();
	}
	
	@Override
	public String getDataTypeIdentifier() {
		return BIGINT;
	}
	
	@Override
	public int getValueHandlingMode() {
		return RANGE;
	}

	@Override
	public Class<Long> getTypeParameterClass() {
		return java.lang.Long.class;
	}
}
