package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hibernate.annotations.Immutable;
import org.reflections.Reflections;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Abstract factory class for product attributes.
 * <p><b>The name of each concrete subclass of this abstract factory must be comprised
 * of the subclass' type parameter class name followed by this class' name.</b></p>
 * @param <E> The attribute's data type
 */
@Entity
@Immutable
@Table(name = "ATTRIBUTE_TYPE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "ATTRIBUTE_DATA_TYPE_ID", columnDefinition = "CHAR(3)")
public abstract class Attribute<E>
extends AbstractEntity<Integer> 
implements Cloneable, AttributeDataTypes, AttributeValueHandlingModes {
	
	/**
	 * Maps type parameter classes to their corresponding subclasses.
	 */
	private static final Map<Class<?>, Class<?>> SUBCLASSES;
	
	/**
	 * <p>Maps the SQL data type primary key (returned by {@link #getDataTypeIdentifier()})
	 * to the name of the parameterised class used by the attribute instance.</p>
	 * <p><b>This map must contain an entry for every parameterised type used in
	 * a subclass of this abstract factory.</b></p>
	 */
	public static final Map<String, Class<?>> TYPE_PARAMETER_CLASSES;

	static {
		Map<Class<?>, Class<?>> subclassMap = new HashMap<>();
		Map<String, Class<?>> typeParameterClassMap = new HashMap<>();

		Reflections reflections = new Reflections(Attribute.class.getPackage().getName());
		for (Class<?> subclass : reflections.getSubTypesOf(Attribute.class)) {
			
			try {
				Attribute<?> attribute = (Attribute<?>) subclass.getDeclaredConstructor().newInstance();
				Class<?> typeParameterClass = attribute.getTypeParameterClass();

				subclassMap.put(typeParameterClass, subclass);
				typeParameterClassMap.put(attribute.getDataTypeIdentifier(), typeParameterClass);
			} catch (Exception e) {
				throw new ExceptionInInitializerError(e);
			}
		}
		
		SUBCLASSES = Collections.unmodifiableMap(subclassMap);
		TYPE_PARAMETER_CLASSES = Collections.unmodifiableMap(typeParameterClassMap);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique = true, nullable = false)
	private String name;
	
	@OneToMany(mappedBy = "id", targetEntity = AttributeValue.class)
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<AttributeValue<E>> getValues() {
		return values;
	}

	/**
	 * Returns the list of values for this attribute. If the handling mode is {@link #RANGE},
	 * only returns the first and last values in the list.
	 */
	public List<AttributeValue<E>> getValuesByHandlingMode() {

		List<AttributeValue<E>> valuesToReturn = null;

		switch (getValueHandlingMode()) {
		case RANGE:
			valuesToReturn = new ArrayList<AttributeValue<E>>();

			AttributeValue<E> min = values.get(0);
			AttributeValue<E> max = values.get(values.size()-1);

			valuesToReturn.add(min);
			if (!min.getValue().equals(max.getValue())) {
				valuesToReturn.add(max);
			}
			break;
		case SET:
			valuesToReturn = values;
		}

		return valuesToReturn;
	}

	public E getValueAt(int index) {
		return values.get(index).getValue();
	}

	@SuppressWarnings("unchecked")
	public void addValue(Integer id, Object value) {

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
			clone.getValues().add(value.clone());
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
