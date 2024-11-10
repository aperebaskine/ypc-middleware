package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.service.impl.AttributeServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class AttributeServiceTest {

	private AttributeService attributeService;
	private Locale locale;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		attributeService = new AttributeServiceImpl();
		locale = TestSuite.getLocale();
	}
	
	@Test
	void testFindById() {
		try {
			AttributeDTO<?> attribute = attributeService.findById(1, locale, true);
			assertEquals(12, attribute.getValues().size());
		} catch (YPCException e) {
			fail(e);
		} 
	}
	
	@Test
	void testFindByName() {
		try {
			AttributeDTO<?> attribute = attributeService.findByName("Brand", locale, true);
			assertEquals(12, attribute.getValues().size());
		} catch (YPCException e) {
			fail(e);
		} 
	}

	@Test
	void testFindByCategoryWithValidCategoryIdAndUnssignedValues() {
		try {
			Map<String, AttributeDTO<?>> attributes =
					attributeService.findByCategory((short) 1, locale, AttributeService.RETURN_UNASSIGNED_VALUES);
			
			AttributeDTO<?> boostFreqAttribute = attributes.get("Boost Frequency (MHz)");
			assertEquals(6500l, boostFreqAttribute.getValueAt(boostFreqAttribute.getValues().size()-1));
		} catch (YPCException e) {
			fail(e);
		}
	}

	@Test
	void testFindByCategoryWithValidCategoryIdAndOnlyAssignedValues() {
		try {
			Map<String, AttributeDTO<?>> attributes =
					attributeService.findByCategory((short) 1, locale, AttributeService.NO_UNASSIGNED_VALUES);
			
			AttributeDTO<?> boostFreq = attributes.get("Boost Frequency (MHz)");
			assertEquals(6000l, boostFreq.getValueAt(boostFreq.getValues().size()-1));
			
		} catch (Exception e) {
			 fail(e);
		}
	}

	@Test
	void testFindByCategoryWithInvalidCategoryId() {
		try {
			Map<String, AttributeDTO<?>> attributes =
					attributeService.findByCategory((short) 0, locale, AttributeService.RETURN_UNASSIGNED_VALUES);
			assertTrue(attributes.isEmpty());
		} catch (Exception e) {
			 fail(e);
		}
	}

	@Test
	void testFindByCategoryWithNullCategoryId() {
		try {
			Map<String, AttributeDTO<?>> attributes =
					attributeService.findByCategory(null, locale, AttributeService.RETURN_UNASSIGNED_VALUES);
			assertNull(attributes);
		} catch (Exception e) {
			 fail(e);
		}
	}

}
