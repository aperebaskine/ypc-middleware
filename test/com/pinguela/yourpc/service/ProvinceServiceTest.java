package com.pinguela.yourpc.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Country;
import com.pinguela.yourpc.model.Province;
import com.pinguela.yourpc.service.impl.CountryServiceImpl;
import com.pinguela.yourpc.service.impl.ProvinceServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class ProvinceServiceTest {

	private CountryService countryService;
	private ProvinceService provinceService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		countryService = new CountryServiceImpl();
		provinceService = new ProvinceServiceImpl();
	}

	@Test
	void testFindByCountryWithValidCountryCodes() {
		try {
			List<Country> countries = countryService.findAll();

			for (Country c : countries) {		
				List<Province> provinces = provinceService.findByCountry(c.getId());

				for (Province p : provinces) {
					assertEquals(c.getId(), p.getCountryId());
				}
			}
		} catch (YPCException e) {
			fail(e);
		} 
	}
	
	@Test
	void testFindByCountryWithUnsupportedCountryCode() {
		try {
			List<Province> provinces = provinceService.findByCountry("PRK");
			assertTrue(provinces.isEmpty());
		} catch (YPCException e) {
			fail(e);
		} 
	}

	@Test
	void testFindByCountryWithInvalidCountryCode() {
		try {
			List<Province> provinces = provinceService.findByCountry("ABC");
			assertTrue(provinces.isEmpty());
		} catch (YPCException e) {
			fail(e);
		} 
	}

	@Test
	void testFindByCountryWithNullCountryCode() {
		try {
			List<Province> provinces = provinceService.findByCountry(null);
			assertNull(provinces);
		} catch (YPCException e) {
			fail(e);
		}
	}

}
