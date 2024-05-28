package com.pinguela.yourpc.model;

import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

public class Category 
extends AbstractValueObject {

	private Short id;
	private String name;
	private Category parent = null;
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
