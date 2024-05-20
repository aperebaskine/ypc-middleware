package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Country;
import com.pinguela.yourpc.service.impl.CountryServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class CountryServiceTest {

	private CountryService countryService;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		countryService = new CountryServiceImpl();
	}

	@Test
	void testFindAll() {
		try {
			List<Country> countries = countryService.findAll();
			assertEquals(27, countries.size());
		} catch (YPCException e) {
			fail(e);
		}
	}

}
