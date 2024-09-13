package com.pinguela.yourpc.model;

public final class DoubleAttribute extends Attribute<Double> {

	DoubleAttribute() {
		super();
	}
	
	@Override
	public String getDataTypeIdentifier() {
		return DECIMAL;
	}
	
	@Override
	public int getValueHandlingMode() {
		return RANGE;
	}

	@Override
	public Class<Double> getTypeParameterClass() {
		return java.lang.Double.class;
	}
}
