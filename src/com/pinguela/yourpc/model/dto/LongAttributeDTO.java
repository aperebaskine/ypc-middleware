package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

public final class LongAttributeDTO
extends AttributeDTO<Long> {

	LongAttributeDTO() {
		super();
	}
	
	@Override
	public void addValue(Long id, String valueStr) {
		super.addValue(id, Long.valueOf(valueStr));
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
