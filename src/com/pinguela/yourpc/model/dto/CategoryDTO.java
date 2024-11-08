package com.pinguela.yourpc.model.dto;

import java.util.Set;

public class CategoryDTO
extends AbstractDTO<Short> {

	private String name;
	private Short parentId;
	private Set<CategoryDTO> children;

	public CategoryDTO() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Short getParentId() {
		return parentId;
	}

	public void setParentId(Short parentId) {
		this.parentId = parentId;
	}

	public Set<CategoryDTO> getChildren() {
		return children;
	}

	public void setChildren(Set<CategoryDTO> children) {
		this.children = children;
	}

}
