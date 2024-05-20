package com.pinguela.yourpc.model;

public class LongAttribute extends Attribute<Long> {

	@Override
	public String getDataTypeIdentifier() {
		return BIGINT;
	}
	
	@Override
	public int getValueHandlingMode() {
		return RANGE;
	}
}
