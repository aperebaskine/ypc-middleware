package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

public final class StringAttributeDTO
extends AttributeDTO<String> {
	
	StringAttributeDTO() {
		super();
	}

	@Override
	public String getDataTypeIdentifier() {
		return AttributeDataTypes.VARCHAR;
	}
	
	@Override
	public int getValueHandlingMode() {
		return AttributeValueHandlingModes.SET;
	}

	@Override
	public Class<String> getTypeParameterClass() {
		return java.lang.String.class;
	}

}
