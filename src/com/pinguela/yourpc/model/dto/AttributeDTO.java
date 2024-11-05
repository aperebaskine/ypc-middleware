package com.pinguela.yourpc.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.constants.AttributeValueHandlingModes;

public abstract class AttributeDTO<T> 
extends AbstractDTO<Integer, Attribute<T>> {
	
	private String name;
	private List<AttributeValueDTO<T>> values;
	
	protected AttributeDTO() {
		values = new ArrayList<AttributeValueDTO<T>>();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<AttributeValueDTO<T>> getValues() {
		return values;
	}

	/**
	 * Returns the list of values for this attribute. If the handling mode is {@link #RANGE},
	 * only returns the first and last values in the list.
	 */
	public List<AttributeValueDTO<T>> getValuesByHandlingMode() {

		List<AttributeValueDTO<T>> valuesToReturn = new ArrayList<AttributeValueDTO<T>>();

		switch (getValueHandlingMode()) {
		case AttributeValueHandlingModes.RANGE:
			AttributeValueDTO<T> min = values.get(0);
			AttributeValueDTO<T> max = values.get(values.size()-1);

			valuesToReturn.add(min);
			if (!min.getValue().equals(max.getValue())) {
				valuesToReturn.add(max);
			}
			break;
		case AttributeValueHandlingModes.SET:
			valuesToReturn.addAll(this.values);
		}

		return valuesToReturn;
	}

	public T getValueAt(int index) {
		return values.get(index).getValue();
	}

	@SuppressWarnings("unchecked")
	public void addValue(Long id, Object value) {

		if (value != null && !getTypeParameterClass().isInstance(value)) {
			throw new IllegalArgumentException("Object type does not match type parameter.");
		}

		if (id == null && value == null) {
			throw new IllegalArgumentException("ID and value cannot both be null.");
		}

		values.add(new AttributeValueDTO<T>(id, (T) value));
	}

	public void addAllValues(Collection<AttributeValueDTO<T>> newValues) {
		values.addAll(newValues);
	}

	public void removeValue(int index) {
		values.remove(index);
	}
	
	public void removeValue(T value) {
		values.remove(values.indexOf(value));
	}

	public void removeValue(AttributeValueDTO<T> value) {
		values.remove(value);
	}

	public void clearValues() {
		values.clear();
	}

	@Override
	@SuppressWarnings("unchecked")
	public AttributeDTO<T> clone() {
		
		AttributeDTO<T> clone;
		
		try {
			clone = (AttributeDTO<T>) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError(e);
		}
		
		clone.values = new ArrayList<AttributeValueDTO<T>>();

		for (AttributeValueDTO<T> value : this.values) {
			clone.getValues().add(value.clone());
		}

		return clone;
	};

	@Override
	public int hashCode() {
		return Objects.hash(getId(), name, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDTO<?> other = (AttributeDTO<?>) obj;
		return Objects.equals(getId(), other.getId()) 
				&& Objects.equals(name, other.name) 
				&& Objects.equals(values, other.values);
	}

	/**
	 * @return The type parameter class corresponding to the attribute class.
	 */
	public abstract Class<T> getTypeParameterClass();

	/**
	 * @return The data type constant from {@link AttributeDataTypes} corresponding
	 * to the parameterised type set for this attribute instance.
	 */
	public abstract String getDataTypeIdentifier();

	/**
	 * @return The constant that determines whether this attribute's values should be
	 * treated as a {@link #RANGE} (with minimum and maximum values), or a {@link #SET}.
	 */
	public abstract int getValueHandlingMode();

}
