package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
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
extends AbstractEntity<Integer> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@OneToMany(mappedBy = "attribute")
	private List<AttributeLocale> i18nData;
	
	@OneToMany(targetEntity = AttributeValue.class)
	@JoinColumn(name = "ATTRIBUTE_TYPE_ID")
	private List<AttributeValue<E>> values;

	protected Attribute() {
		values = new ArrayList<AttributeValue<E>>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<AttributeLocale> getI18nData() {
		return i18nData;
	}
	
	public void setI18nData(List<AttributeLocale> i18nData) {
		this.i18nData = i18nData;
	}

	public List<AttributeValue<E>> getValues() {
		return values;
	}

}
