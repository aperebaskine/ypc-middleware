package com.pinguela.yourpc.model;

public class DoubleAttribute extends Attribute<Double> {

	@Override
	public String getDataTypeIdentifier() {
		return DECIMAL;
	}
	
	@Override
	public int getValueHandlingMode() {
		return RANGE;
	}
}
