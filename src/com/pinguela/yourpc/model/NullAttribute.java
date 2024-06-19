package com.pinguela.yourpc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	public List<AttributeValue<String>> getValues() {
		return Collections.unmodifiableList(super.getValues());
	}

	@Override
	public List<AttributeValue<String>> getTrimmedValues() {
		return getValues();
	}

	@Override
	public void addValue(AttributeValue<String> attributeValue) {}

	@Override
	public String getDataTypeIdentifier() {
		return VARCHAR;
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
