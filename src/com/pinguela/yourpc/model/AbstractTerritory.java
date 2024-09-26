package com.pinguela.yourpc.model;

import java.util.Objects;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@Immutable
@MappedSuperclass
public abstract class AbstractTerritory<PK extends Comparable<PK>> 
extends AbstractEntity<PK> {
	
	@Id
	private PK id;
	
	@Column(nullable = false)
	private String name;
	
	public AbstractTerritory() {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractTerritory<?> other = (AbstractTerritory<?>) obj;
		return Objects.equals(id, other.id);
	}	

}
