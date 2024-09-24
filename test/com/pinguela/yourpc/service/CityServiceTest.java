package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.service.impl.CityServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class CityServiceTest {

	private CityService cityService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		cityService = new CityServiceImpl();
	}

	@Test
	void testFindByProvinceWithValidProvinceId() {
		try {
			List<City> cities = cityService.findByProvince(1);
			for (City c : cities) {
				assertEquals(1, c.getProvince().getId());
			}
		} catch (YPCException e) {
			fail(e);
		}
	}

	@Test
	void testFindByProvinceWithInvalidProvinceId() {
		try {
			List<City> cities = cityService.findByProvince(0);
			assertTrue(cities.isEmpty());
		} catch (YPCException e) {
			fail(e);
		}
	}

	@Test
	void testFindByProvinceWithNullProvinceId() {
		assertThrows(NullPointerException.class, () -> {
			cityService.findByProvince(null);
		});
	}

}
