package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract factory class for product attributes.
 * <p><b>The name of each concrete subclass of this abstract factory must be comprised
 * of the subclass' type parameter class name followed by this class' name.</b></p>
 * @param <E> The attribute's data type
 */
public abstract sealed class Attribute<E>
extends AbstractValueObject 
implements Cloneable, AttributeDataTypes, AttributeValueHandlingModes 
permits LongAttribute, StringAttribute, DoubleAttribute, BooleanAttribute, NullAttribute {

	/**
	 * <p>Maps the SQL data type primary key (returned by {@link #getDataTypeIdentifier()})
	 * to the name of the parameterised class used by the attribute instance.</p>
	 * <p><b>This map must contain an entry for every parameterised type used in
	 * a subclass of this abstract factory.</b></p>
	 */
	public static final Map<String, Class<?>> TYPE_PARAMETER_CLASSES;
	
	/**
	 * Maps type parameter classes to their corresponding subclasses.
	 */
	private static final Map<Class<?>, Class<?>> SUBCLASSES;

	static {
		Map<String, Class<?>> typeParameterClassMap = new HashMap<>();
		Map<Class<?>, Class<?>> subclassMap = new HashMap<>();

		for (Class<?> clazz : Attribute.class.getPermittedSubclasses()) {
			
			if (clazz.isAssignableFrom(NullAttribute.class)) {
				continue;
			}

			try {
				Attribute<?> attribute = (Attribute<?>) clazz.getDeclaredConstructor().newInstance();
				Class<?> typeParameterClass = attribute.getTypeParameterClass();

				typeParameterClassMap.put(attribute.getDataTypeIdentifier(), typeParameterClass);
				subclassMap.put(typeParameterClass, clazz);
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}

		}
		
		TYPE_PARAMETER_CLASSES = Collections.unmodifiableMap(typeParameterClassMap);
		SUBCLASSES = Collections.unmodifiableMap(subclassMap);
	}

	private String name;
	private List<AttributeValue<E>> values;

	protected Attribute() {
		values = new ArrayList<AttributeValue<E>>();
	}

	public static final Attribute<?> getInstance(String dataType) {
		return getInstance(TYPE_PARAMETER_CLASSES.get(dataType));
	}

	@SuppressWarnings("unchecked")
	public static final <E> Attribute<E> getInstance(String name, E... values) {

		Class<E> typeParameter = (Class<E>) values.getClass().getComponentType();

		if (values == null || !TYPE_PARAMETER_CLASSES.containsValue(typeParameter)) {
			throw new IllegalArgumentException("Type of values must match one of the allowed type parameters.");
		}

		Attribute<E> attribute = getInstance(typeParameter);
		attribute.setName(name);

		for (E value : values) {
			attribute.addValue(null, value);
		}

		return attribute;
	}

	@SuppressWarnings("unchecked")
	public static final <E> Attribute<E> getInstance(Class<E> typeParameterClass) {

		if (!TYPE_PARAMETER_CLASSES.containsValue(typeParameterClass)) {
			throw new IllegalArgumentException(
					String.format("Cannot instantiate attribute using type parameter %s.", typeParameterClass.getName()));
		}

		try { 
			return (Attribute<E>) SUBCLASSES.get(typeParameterClass).getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new IllegalStateException(String.format("Exception thrown while creating instance: %s", e.getMessage()), e);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AttributeValue<E>> getAllValues() {
		return values;
	}

	/**
	 * Returns the list of values for this attribute. If the handling mode is {@link #RANGE},
	 * only returns the first and last values in the list.
	 */
	public List<AttributeValue<E>> getValuesByHandlingMode() {

		List<AttributeValue<E>> trimmedValues = null;

		switch (getValueHandlingMode()) {
		case RANGE:
			trimmedValues = new ArrayList<AttributeValue<E>>();

			AttributeValue<E> min = values.get(0);
			AttributeValue<E> max = values.get(values.size()-1);

			trimmedValues.add(min);
			if (!min.getValue().equals(max.getValue())) {
				trimmedValues.add(max);
			}
			break;
		case SET:
			trimmedValues = values;
		}

		return trimmedValues;
	}

	public E getValueAt(int index) {
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

		AttributeValue<E> attributeValue = new AttributeValue<E>(id, (E) value);
		values.add(attributeValue);
	}

	public void addValue(AttributeValue<E> attributeValue) {
		values.add(attributeValue);
	}

	public void addAllValues(Collection<AttributeValue<E>> newValues) {
		values.addAll(newValues);
	}

	public void removeValue(int index) {
		values.remove(index);
	}

	public void removeValue(AttributeValue<?> value) {
		values.remove(value);
	}

	public void clearValues() {
		values.clear();
	}

	@Override
	public Attribute<E> clone() {
		Attribute<E> clone = Attribute.getInstance(getTypeParameterClass());
		clone.setName(this.name);

		for (AttributeValue<E> value : this.values) {
			clone.getAllValues().add(value.clone());
		}

		return clone;
	};

	@Override
	public int hashCode() {
		return Objects.hash(name, values);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Attribute<?> other = (Attribute<?>) obj;
		return Objects.equals(name, other.name) && Objects.equals(values, other.values);
	}

	/**
	 * @return The type parameter class corresponding to the attribute class.
	 */
	public abstract Class<E> getTypeParameterClass();

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
