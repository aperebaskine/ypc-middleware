package com.pinguela.yourpc.model;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import org.hibernate.annotations.Immutable;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapKey;
import jakarta.persistence.OneToMany;

@Entity
@Immutable
public class Category 
extends AbstractEntity<Short> {

	@Id
	private Short id;
	
	@OneToMany(mappedBy = "category")
	private List<CategoryLocale> categoryLocale;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	private Category parent = null;
	
	@MapKey(name = "id")
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
	private Map<Short, Category> children = null;

	public Category() {
		children = new TreeMap<Short, Category>();
	}

	public Short getId() {
		return id;
	}
	
	public void setId(Short id) {
		this.id = id;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Map<Short, Category> getChildren() {
		return children;
	}

	public void setChildren(Map<Short, Category> children) {
		this.children = children;
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
		Category other = (Category) obj;
		return Objects.equals(id, other.id);
	}
	
}
