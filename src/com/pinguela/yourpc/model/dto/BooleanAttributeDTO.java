package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

public final class BooleanAttributeDTO 
extends AttributeDTO<Boolean> {
	
	BooleanAttributeDTO() {
		super();
	}
	
	@Override
	public void addValue(Long id, String valueStr) {
		super.addValue(id, Boolean.valueOf(valueStr));
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
