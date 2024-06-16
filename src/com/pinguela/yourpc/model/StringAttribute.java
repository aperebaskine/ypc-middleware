package com.pinguela.yourpc.model;

public class StringAttribute extends Attribute<String> {

	@Override
	public String getDataTypeIdentifier() {
		return VARCHAR;
	}
	
	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
