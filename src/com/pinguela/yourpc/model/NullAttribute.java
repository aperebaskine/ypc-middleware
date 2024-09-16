package com.pinguela.yourpc.model;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.lang.model.type.NullType;

public final class NullAttribute extends Attribute<NullType> {
	
	public NullAttribute() {
		super.setName("");
	}

	@Override
	public void setName(String name) {}

	@Override
	public void addValue(Long id, Object value) {}

	@Override
	public void addAllValues(Collection<AttributeValue<NullType>> newValues) {}

	@Override
	public void removeValue(int index) {}

	@Override
	public void removeValue(AttributeValue<?> value) {}

	@Override
	public void clearValues() {}

	@Override
	public Attribute<NullType> clone() {
		return this;
	}
	
	@Override
	public List<AttributeValue<NullType>> getValues() {
		return Collections.unmodifiableList(super.getValues());
	}

	@Override
	public List<AttributeValue<NullType>> getValuesByHandlingMode() {
		return getValues();
	}

	@Override
	public void addValue(AttributeValue<NullType> attributeValue) {}

	@Override
	public Class<NullType> getTypeParameterClass() {
		return javax.lang.model.type.NullType.class;
	}

	@Override
	public String getDataTypeIdentifier() {
		return "";
	}

	@Override
	public int getValueHandlingMode() {
		return SET;
	}

}
