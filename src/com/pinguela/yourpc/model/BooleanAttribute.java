package com.pinguela.yourpc.model;

public class BooleanAttribute extends Attribute<Boolean> {

	@Override
	public String getDataTypeIdentifier() {
		return BOOLEAN;
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
