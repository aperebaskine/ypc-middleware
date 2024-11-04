package com.pinguela.yourpc.model;

import org.hibernate.annotations.NaturalId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ATTRIBUTE_TYPE_LOCALE")
public class AttributeLocale 
extends AbstractEntity<Integer> 
implements Cloneable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@NaturalId
	@Column(name = "ATTRIBUTE_TYPE_ID")
	private Attribute<?> attribute;
	
	@ManyToOne
	@NaturalId
	private Locale locale;
	
	@Column(nullable = false)
	private String name;
	
	@Override
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public Attribute<?> getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute<?> attribute) {
		this.attribute = attribute;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
