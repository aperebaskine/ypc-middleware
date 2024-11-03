package com.pinguela.yourpc.model;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue(value = AttributeDataTypes.VARCHAR)
public final class StringAttribute extends Attribute<String> {
	
	StringAttribute() {
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
