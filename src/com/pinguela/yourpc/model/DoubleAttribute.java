package com.pinguela.yourpc.model;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.DECIMAL)
public final class DoubleAttribute extends Attribute<Double> {

	DoubleAttribute() {
		super();
	}
	
	@Override
	public String getDataTypeIdentifier() {
		return AttributeDataTypes.DECIMAL;
	}
	
	@Override
	public int getValueHandlingMode() {
		return AttributeValueHandlingModes.RANGE;
	}

	@Override
	public Class<Double> getTypeParameterClass() {
		return java.lang.Double.class;
	}
}
