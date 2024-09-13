package com.pinguela.yourpc.model;

public final class StringAttribute extends Attribute<String> {
	
	StringAttribute() {
		super();
	}

	@Override
	public String getDataTypeIdentifier() {
		return VARCHAR;
	}
	
	@Override
	public int getValueHandlingMode() {
		return SET;
	}

	@Override
	public Class<String> getTypeParameterClass() {
		return java.lang.String.class;
	}

}
