package com.pinguela.yourpc.model;

import java.util.Collection;

public class NullAttribute extends Attribute<String> {
	
	public NullAttribute() {
		super.setName("");
	}

	@Override
	public void setName(String name) {}

	@Override
	public String getValueAt(int index) {
		return null;
	}

	@Override
	public void addValue(Long id, Object value) {}

	@Override
	public void addAll(Collection<AttributeValue<String>> newValues) {}

	@Override
	public void removeValue(int index) {}

	@Override
	public void removeValue(AttributeValue<?> value) {}

	@Override
	public void removeAllValues() {}

	@Override
	public Attribute<String> clone() {
		return this;
	}

	@Override
	public Attribute<String> trim() {
		return this;
	}

	@Override
	public String getDataTypeIdentifier() {
		return VARCHAR;
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
