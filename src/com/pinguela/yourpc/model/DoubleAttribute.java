package com.pinguela.yourpc.model;

public class DoubleAttribute extends Attribute<Double> {

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
}
