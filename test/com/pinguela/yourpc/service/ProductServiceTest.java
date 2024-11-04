package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.DataException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.ProductDTO;
import com.pinguela.yourpc.service.impl.ProductServiceImpl;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.DateUtils;

@TestInstance(Lifecycle.PER_CLASS)
class ProductServiceTest {

	private ProductService productService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		productService = new ProductServiceImpl();
	}

	@Nested
	class TestFindById {

		@Test
		void testWithValidId() {
			try {
				ProductDTO p = productService.findById(1l, null);
				assertEquals(1, p.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				ProductDTO p = productService.findById(0l, null);
				assertNull(p);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				ProductDTO p = productService.findById(null, null);
				assertNull(p);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestFindBy {

		private Attribute<String> varcharAttribute;
		private Attribute<Long> bigintAttribute;
		private Attribute<Double> decimalAttribute;
		private Attribute<Boolean> booleanAttribute;

		{
			varcharAttribute = Attribute.getInstance(String.class);
			varcharAttribute.setName("Brand");
			varcharAttribute.addValue(null, "AMD");

			bigintAttribute = Attribute.getInstance(Long.class);
			bigintAttribute.setName("Number of Cores");
			bigintAttribute.addValue(null, 12l);
			bigintAttribute.addValue(null, 20l);

			decimalAttribute = Attribute.getInstance(Double.class);
			decimalAttribute.setName("Voltage (V)");
			decimalAttribute.addValue(null, 1.1d);
			decimalAttribute.addValue(null, 1.2d);

			booleanAttribute = Attribute.getInstance(Boolean.class);
			booleanAttribute.setName("Integrated Graphics");
			booleanAttribute.addValue(null, false);
		}

		private ProductCriteria criteria;
		private int pos;
		private int pageSize;

		private Results<ProductDTO> results;

		@BeforeEach
		void setUp() {
			criteria = new ProductCriteria();
			pos = 1;
			pageSize = 10;
		}

		@AfterEach
		void tearDown() {
			results = null;
		}

		@Test
		void testWithNullCriteria() {
			assertThrows(NullPointerException.class, () -> productService.findBy(null, pos, pageSize));
		}

		@Test
		void testFindByEmptyCriteria() {
			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertTrue(results.getResultCount() >= 54);
				assertEquals(pageSize, results.getPage().size());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidPos() {
			pos = 0;
			assertThrows(IllegalArgumentException.class, () -> productService.findBy(criteria, pos, pageSize));
		}

		@Test
		void testWithOutOfBoundsPos() {
			try {
				pos = Integer.MAX_VALUE - pageSize + 1;
				results = productService.findBy(criteria, pos, pageSize);
				assertTrue(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidPageSize() {
			try {
				pageSize = 0;
				results = productService.findBy(criteria, pos, pageSize);
				assertTrue(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithMinimumPageSize() {
			try {
				pageSize = 1;
				results = productService.findBy(criteria, pos, pageSize);
				assertEquals(pageSize, results.getPage().size());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByName() {
			try {
				criteria.setName("rYzEn");
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getName().toLowerCase().contains("rYzEn".toLowerCase()));
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByLaunchDateMin() {
			try {
				Date date = DateUtils.getDate(2023, Calendar.AUGUST, 1); 
				criteria.setLaunchDateMin(date);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getLaunchDate().compareTo(date) >= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByLaunchDateMax() {
			try {
				Date date = DateUtils.getDate(2023, Calendar.AUGUST, 1); 
				criteria.setLaunchDateMax(date);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getLaunchDate().compareTo(date) <= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByStockMin() {
			try {
				criteria.setStockMin(40);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getStock() >= criteria.getStockMin());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByStockMax() {
			try {
				criteria.setStockMax(30);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getStock() <= criteria.getStockMax());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByZeroStockMin() {
			try {
				criteria.setStockMin(0);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByZeroStockMax() {
			try {
				criteria.setStockMax(0);
				results = productService.findBy(criteria, pos, pageSize);
				assertTrue(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByPriceMin() {
			try {
				criteria.setPriceMin(900.0d);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getSalePrice().compareTo(criteria.getPriceMin()) >= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByPriceMax() {
			try {
				criteria.setPriceMax(250.0d);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(p.getSalePrice().compareTo(criteria.getPriceMax()) <= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByZeroPriceMin() {
			try {
				criteria.setPriceMin(0.0d);
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByZeroPriceMax() {
			try {
				criteria.setPriceMax(0.0d);
				results = productService.findBy(criteria, pos, pageSize);
				assertTrue(results.getPage().isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByCategoryId() {
			try {
				criteria.setCategoryId((short) 11);
				Set<Short> categoryHierarchy = CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet();

				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());
				for (ProductDTO p : results.getPage()) {
					assertTrue(categoryHierarchy.contains(p.getCategoryId()));
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByVarcharAttribute() {

			criteria.getAttributes().put(varcharAttribute.getName(), varcharAttribute);
			criteria.setCategoryId((short) 1);

			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {
					String brand = (String) p.getAttributes().get(varcharAttribute.getName()).getValueAt(0);
					assertEquals(varcharAttribute.getValueAt(0), brand);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByBigintAttribute() {

			criteria.getAttributes().put(bigintAttribute.getName(), bigintAttribute);

			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {
					Long cores = (Long) p.getAttributes().get(bigintAttribute.getName()).getValueAt(0);
					assertTrue(cores >= bigintAttribute.getValueAt(0) && cores <= bigintAttribute.getValueAt(1));
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}  
		}

		@Test
		void testFindByDecimalAttribute() {

			criteria.getAttributes().put(decimalAttribute.getName(), decimalAttribute);

			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {
					Double voltage = (Double) p.getAttributes().get(decimalAttribute.getName()).getValueAt(0);
					assertTrue(voltage.compareTo(decimalAttribute.getValueAt(0)) >= 0 
							&& voltage.compareTo(decimalAttribute.getValueAt(1)) <= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}    
		}

		@Test
		void testFindByBooleanAttribute() {

			criteria.getAttributes().put(booleanAttribute.getName(), booleanAttribute);

			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				for (ProductDTO p : results.getPage()) {					
					Boolean hasIntegratedGraphics = 
							(Boolean) p.getAttributes().get(booleanAttribute.getName()).getValueAt(0);
					assertEquals(booleanAttribute.getValueAt(0), hasIntegratedGraphics);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}       
		}

		@Test
		void testFindByMultipleCriteria() {

			criteria.setCategoryId((short) 1);
			criteria.setStockMin(20);
			criteria.setPriceMax(600.0d);
			criteria.getAttributes().put(varcharAttribute.getName(), varcharAttribute);
			criteria.getAttributes().put(booleanAttribute.getName(), booleanAttribute);

			try {
				results = productService.findBy(criteria, pos, pageSize);
				assertFalse(results.getPage().isEmpty());

				Set<Short> categoryHierarchy = CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet();

				for (ProductDTO p : results.getPage()) {
					assertTrue(categoryHierarchy.contains(p.getCategoryId()));
					assertTrue(p.getStock() >= criteria.getStockMin());
					assertTrue(p.getSalePrice() <= criteria.getPriceMax());

					String brand = (String) p.getAttributes().get(varcharAttribute.getName()).getValueAt(0);
					assertEquals(varcharAttribute.getValueAt(0), brand);

					Boolean hasIntegratedGraphics = (Boolean) p.getAttributes().get(booleanAttribute.getName()).getValueAt(0);
					assertEquals(criteria.getAttributes().get(booleanAttribute.getName()).getValueAt(0), hasIntegratedGraphics);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}  
		}

	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class TestCreate {

		private ProductDTO p;

		@BeforeEach
		void setUp() {

			p = new ProductDTO();

			p.setName("Intel Core i9-15900k");

			p.setCategoryId((short) 1);

			p.setDescription("TESTCREATE" +System.currentTimeMillis());
			p.setLaunchDate(new java.sql.Date(DateUtils.getDate(2020, Calendar.FEBRUARY, 1).getTime()));
			p.setStock(50);
			p.setPurchasePrice(449.99d);
			p.setSalePrice(649.99d);

			Attribute<String> firstAttribute = Attribute.getInstance(String.class);
			firstAttribute.setName("Memory Type");
			firstAttribute.addValue(97l, null);
			firstAttribute.addValue(98l, null);

			p.getAttributes().put(firstAttribute.getName(), firstAttribute);

			Attribute<Double> secondAttribute = Attribute.getInstance(Double.class);
			secondAttribute.setName("Voltage (V)");
			secondAttribute.addValue(146l, null);

			p.getAttributes().put(secondAttribute.getName(), secondAttribute);

			Attribute<String> thirdAttribute = Attribute.getInstance(String.class);
			thirdAttribute.setName("Brand");
			thirdAttribute.addValue(null, "TESTCREATE" + System.currentTimeMillis());

			p.getAttributes().put(thirdAttribute.getName(), thirdAttribute);

			Attribute<Long> fourthAttribute = Attribute.getInstance(Long.class);
			fourthAttribute.setName("Boost Frequency (MHz)");
			fourthAttribute.addValue(null, 6000L);

			p.getAttributes().put(fourthAttribute.getName(), fourthAttribute);

			Attribute<Boolean> fifthAttribute = Attribute.getInstance(Boolean.class);
			fifthAttribute.setName("Integrated Graphics");
			fifthAttribute.addValue(null, true);

			p.getAttributes().put(fifthAttribute.getName(), fifthAttribute);

		}

		@Test
		void testCreateValidProduct() {
			try {
				Long id = productService.create(p);
				ProductDTO q = productService.findById(id, null);
				assertEquals(p.getDescription(), q.getDescription());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testCreateWithoutAttributes() {
			try {
				p.getAttributes().clear();

				Long id = productService.create(p);
				ProductDTO q = productService.findById(id, null);
				assertEquals(p.getDescription(), q.getDescription());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testCreateWithSingleAttribute() {
			try {
				Attribute<?> attribute = p.getAttributes().get("Brand");
				p.getAttributes().clear();
				p.getAttributes().put(attribute.getName(), attribute);

				Long id = productService.create(p);
				ProductDTO q = productService.findById(id, null);
				assertEquals(p.getDescription(), q.getDescription());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullProduct() {
			assertThrows(NullPointerException.class, () -> productService.create(null));
		}

		@Test
		void testCreateWithNullName() {
			p.setName(null);
			assertThrows(DataException.class, () -> productService.create(p));
		}

		@Test
		void testCreateWithInvalidCategoryId() {
			p.setCategoryId((short) 0);
			assertThrows(DataException.class, () -> productService.create(p));
		}
		
		@AfterAll
		void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
	}


	@Test
	void testUpdate() {
		try {
			ProductDTO p = productService.findById(1l, null);
			p.setDescription("TEST" +System.currentTimeMillis());
			assertTrue(productService.update(p));

			ProductDTO q = productService.findById(p.getId(), null);
			assertEquals(p.getDescription(), q.getDescription());

		} catch (YPCException e) {
			fail(e.getMessage(), e);
		}
	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	public class TestDelete {

		@AfterAll
		void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}

		@Test
		void testDelete() {
			try {
				ProductDTO p = productService.findById(1l, null);
				assertTrue(productService.delete(p.getId()));			
				assertNull(productService.findById(p.getId(), null));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Test
	void testGetRanges() {

		ProductRanges ranges = null;
		ProductCriteria criteria = new ProductCriteria();
		criteria.setCategoryId((short) 1);

		try {
			ranges = productService.getRanges(criteria);
		} catch (YPCException e) {
			fail(e.getMessage(), e);
		}

		assertNotNull(ranges);
		assertEquals(300.0d, ranges.getPriceMin(), 0.0002d);
		assertEquals(800.0d, ranges.getPriceMax(), 0.0002d);
		assertEquals(15, ranges.getStockMin());
		assertEquals(100, ranges.getStockMax());
		assertEquals(DateUtils.getDate(2022, Calendar.NOVEMBER, 16).getTime(), ranges.getLaunchDateMin().getTime());
		assertEquals(DateUtils.getDate(2023, Calendar.NOVEMBER, 16).getTime(), ranges.getLaunchDateMax().getTime());
	}

}
