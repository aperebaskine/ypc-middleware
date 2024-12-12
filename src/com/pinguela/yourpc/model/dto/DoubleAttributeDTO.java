package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

public final class DoubleAttributeDTO
extends AttributeDTO<Double> {

	DoubleAttributeDTO() {
		super();
	}
	
	@Override
	protected Double parseValue(String valueStr) {
		return Double.valueOf(valueStr);
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
