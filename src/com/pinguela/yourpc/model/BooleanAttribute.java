package com.pinguela.yourpc.model;

public final class BooleanAttribute 
extends Attribute<Boolean> {
	
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

	@Override
	public Class<Boolean> getTypeParameterClass() {
		return java.lang.Boolean.class;
	}

}
