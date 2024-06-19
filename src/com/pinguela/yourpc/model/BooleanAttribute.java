package com.pinguela.yourpc.model;

public class BooleanAttribute extends Attribute<Boolean> {
	
	BooleanAttribute() {
		super();
	}

	@Override
	public String getDataTypeIdentifier() {
		return BOOLEAN;
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
