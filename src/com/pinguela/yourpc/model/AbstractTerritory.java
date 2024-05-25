package com.pinguela.yourpc.model;

import java.util.Objects;

public abstract class AbstractTerritory<PK>
extends AbstractValueObject {
	
	private PK id;
	private String name;
	
	public AbstractTerritory() {
		this(null, null);
	}
	
	public AbstractTerritory(PK id) {
		this(id, null);
	}
	
	public AbstractTerritory(PK id, String name) {
		setId(id);
		setName(name);
	}
	
	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTerritory<PK> other = (AbstractTerritory<PK>) obj;
		return Objects.equals(id, other.id);
	}
	
	

}
