package com.pinguela.yourpc.util;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.model.dto.CategoryDTO;
import com.pinguela.yourpc.service.CategoryService;
import com.pinguela.yourpc.service.impl.CategoryServiceImpl;

public class CategoryUtils {

	private static Logger logger = LogManager.getLogger(CategoryUtils.class);
	public static final Map<Short, CategoryDTO> CATEGORIES;

	static {
		Map<Short, CategoryDTO> categories = initializeMap();
		CATEGORIES = Collections.unmodifiableMap(categories);
	}

	private static final Map<Short, CategoryDTO> initializeMap() {
		try {
			CategoryService categoryService = new CategoryServiceImpl();
			return categoryService.findAll(Locale.getDefault());
		} catch (Exception e) {
			logger.error(e);
			return Collections.emptyMap();
		}
	}

	public static final Map<Short, CategoryDTO> getUpperHierarchy(Short categoryId) {

		if (categoryId == null) {
			return Collections.emptyMap();
		}

		Map<Short, CategoryDTO> results = new TreeMap<Short, CategoryDTO>();
		CategoryDTO c = CATEGORIES.get(categoryId);

		if (c != null) {
			results.put(categoryId, c);
			if (c.getParentId() != null) {
				putParents(results, c);
			}
		}

		return results;
	}

	public static final Map<Short, CategoryDTO> getLowerHierarchy(Short categoryId) {

		if (categoryId == null) {
			return Collections.emptyMap();
		}

		Map<Short, CategoryDTO> results = new TreeMap<Short, CategoryDTO>();
		CategoryDTO c = CATEGORIES.get(categoryId);

		if (c != null) {
			results.put(categoryId, c);
			if (c.getChildren() != null) {
				putChildren(results, c);
			}
		}

		return results;
	}

	private static final void putParents(Map<Short, CategoryDTO> map, CategoryDTO c) {

		if (c.getParentId() != null) { // Add parent's parent
			CategoryDTO parent = CATEGORIES.get(c.getParentId());
			map.put(parent.getId(), parent);

			putParents(map, parent);
		}	
	}

	private static final void putChildren(Map<Short, CategoryDTO> map, CategoryDTO c) {

		if (c.getChildren() == null || c.getChildren().isEmpty()) {
			return;
		}

		for (CategoryDTO child : c.getChildren()) {
			map.put(child.getId(), child);
			putChildren(map, child);
		} 
	}

}
