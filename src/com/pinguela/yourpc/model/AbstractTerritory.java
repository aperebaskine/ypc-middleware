package com.pinguela.yourpc.model;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@SuppressWarnings("serial")
@MappedSuperclass
public abstract class AbstractTerritory<PK>
extends AbstractValueObject {
	
	private @Id PK id;
	private String name;
	
	public AbstractTerritory() {
	}
	
	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	@Column(nullable = false, length = 40)
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
