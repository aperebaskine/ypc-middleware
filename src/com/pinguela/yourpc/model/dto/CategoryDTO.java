package com.pinguela.yourpc.model.dto;

import java.util.Set;

import com.pinguela.yourpc.model.Category;

public class CategoryDTO
extends AbstractDTO<Short, Category> {
	
	private String name;
	private Short parentId;
	private Set<Short> childrenIds;
	
	public CategoryDTO() {
	}

	public CategoryDTO(String name, Short parentId, Set<Short> childrenIds) {
		super();
		this.name = name;
		this.parentId = parentId;
		this.childrenIds = childrenIds;
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

	public Set<Short> getChildrenIds() {
		return childrenIds;
	}

	public void setChildrenIds(Set<Short> childrenIds) {
		this.childrenIds = childrenIds;
	}

}
