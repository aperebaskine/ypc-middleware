package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.service.impl.CategoryServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class CategoryServiceTest {
	
	private CategoryService categoryService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		categoryService = new CategoryServiceImpl();
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindAll() {
		try {
			Map<Short, Category> categories = categoryService.findAll(null);
			assertEquals(17, categories.size());
		} catch (YPCException e) {
			fail(e);
		}
	}

}
