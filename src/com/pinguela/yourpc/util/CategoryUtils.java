package com.pinguela.yourpc.util;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.service.CategoryService;
import com.pinguela.yourpc.service.impl.CategoryServiceImpl;

public class CategoryUtils {

	private static Logger logger = LogManager.getLogger(CategoryUtils.class);
	public static final Map<Short, Category> CATEGORIES;

	static {
		Map<Short, Category> categories = initializeMap();
		CATEGORIES = Collections.unmodifiableMap(categories);
	}

	private static final Map<Short, Category> initializeMap() {
		try {
			CategoryService categoryService = new CategoryServiceImpl();
			return categoryService.findAll();
		} catch (Exception e) {
			logger.error(e);
			return Collections.emptyMap();
		}
	}

	public static final Map<Short, Category> getUpperHierarchy(Short categoryId) {

		Map<Short, Category> results = new TreeMap<Short, Category>();
		Category c = CATEGORIES.get(categoryId);

		if (c != null) {
			results.put(categoryId, c);
			if (c.getParent() != null) {
				putParents(results, c);
			}
		}
		
		return results;
	}

	public static final Map<Short, Category> getLowerHierarchy(Short categoryId) {

		Map<Short, Category> results = new TreeMap<Short, Category>();
		Category c = CATEGORIES.get(categoryId);

		if (c != null) {
			results.put(categoryId, c);
			if (c.getChildren() != null) {
				putChildren(results, c);
			}
		}
		
		return results;
	}

	private static final void putParents(Map<Short, Category> map, Category c) {

		Category parent = c.getParent();
		map.put(c.getId(), parent);

		if (c.getParent() != null) { // Add parent's parent
			putParents(map, parent);
		}	
	}

	private static final void putChildren(Map<Short, Category> map, Category c) {

		if (c.getChildren() == null || c.getChildren().isEmpty()) {
			return;
		}
		
		for (Category child : c.getChildren().values()) {
			map.put(child.getId(), child);
			putChildren(map, child);
		} 
	}

}
