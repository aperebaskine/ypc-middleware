package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.service.impl.CustomerOrderServiceImpl;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;
import com.pinguela.yourpc.util.DateUtils;

@TestInstance(Lifecycle.PER_CLASS)
class CustomerOrderServiceTest {

	private CustomerOrderService customerOrderService;
	private CustomerService customerService;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		customerOrderService = new CustomerOrderServiceImpl();
		customerService = new CustomerServiceImpl();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Nested
	class TestFindById {
		
		private Locale locale = TestSuite.getLocale();

		@Test
		void testWithValidId() {
			try {
				CustomerOrder o = customerOrderService.findById(1l, locale);
				assertEquals(1l, o.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				CustomerOrder o = customerOrderService.findById(0l, locale);
				assertNull(o);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				CustomerOrder o = customerOrderService.findById(null, locale);
				assertNull(o);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}
	
	@Nested
	class TestFindByCustomer {
		
		private Locale locale = TestSuite.getLocale();

		@Test
		void testWithValidId() {
			try {
				List<CustomerOrder> results = customerOrderService.findByCustomer(1, locale);
				assertEquals(5l, results.size());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				List<CustomerOrder> results = customerOrderService.findByCustomer(0, locale);
				assertTrue(results.isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				List<CustomerOrder> results = customerOrderService.findByCustomer(null, locale);
				assertNull(results);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}


	@Nested
	class TestFindBy {

		private Locale locale = TestSuite.getLocale();
		private CustomerOrderCriteria criteria;

		@BeforeEach
		void setUp() {
			criteria = new CustomerOrderCriteria();
		}

		@Test
		void testFindByEmptyCriteria() {
			try {
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertTrue(orders.size() >= 22);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByNullCriteria() {
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.findBy(null, locale));
		}

		@Test
		void testFindByCustomerId() {
			try {
				criteria.setCustomerId(1);
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertEquals(criteria.getCustomerId(), o.getCustomerId());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByCustomerEmail() {
			try {
				criteria.setCustomerEmail("cant@see.me");
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					String email = customerService.findById(o.getCustomerId()).getEmail();
					assertEquals(criteria.getCustomerEmail(), email);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByMinAmount() {
			try {
				criteria.setMinAmount(600.0d);
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertTrue(o.getTotalPrice().compareTo(criteria.getMinAmount()) >= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByMaxAmount() {
			try {
				criteria.setMaxAmount(500.0d);
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertTrue(o.getTotalPrice().compareTo(criteria.getMaxAmount()) <= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByMinDate() {
			try {
				Date date = DateUtils.getDate(2023, Calendar.NOVEMBER, 1);
				criteria.setMinDate(date);
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertTrue(o.getOrderDate().compareTo(date) >= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByMaxDate() {
			try {
				Date date = DateUtils.getDate(2023, Calendar.NOVEMBER, 1);
				criteria.setMaxDate(date);
				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertTrue(o.getOrderDate().compareTo(date) <= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByState() {
			for (String state : new String[]{"PND", "PRS", "DEL", "SPD"}) {
				try {
					criteria.setState(state);
					List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
					assertFalse(orders.isEmpty());

					for (CustomerOrder o : orders) {
						assertEquals(criteria.getState(), o.getState());
					}
				} catch (YPCException e) {
					fail(e.getMessage(), e);
				}
			}
		}

		@Test
		void testFindByMultipleCriteria() {
			try {
				Date date = DateUtils.getDate(2023, Calendar.DECEMBER, 1);
				criteria.setMinDate(date);
				criteria.setCustomerEmail("cant@see.me");
				criteria.setState("PND");
				criteria.setMinAmount(1000.0d);

				List<CustomerOrder> orders = customerOrderService.findBy(criteria, locale);
				assertFalse(orders.isEmpty());

				for (CustomerOrder o : orders) {
					assertTrue(o.getOrderDate().compareTo(date) >= 0);

					String email = customerService.findById(o.getCustomerId()).getEmail();
					assertEquals(criteria.getCustomerEmail(), email);

					assertEquals(criteria.getState(), o.getState());
					assertTrue(o.getTotalPrice().compareTo(criteria.getMinAmount()) >= 0);
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class TestCreate {

		private Locale locale = TestSuite.getLocale();
		private CustomerOrder o;
		
		@AfterAll
		void tearDownAfterClass() throws Exception {
			TestSuite.initializeTestDatabase();
		}

		@BeforeEach
		void setUp() {

			o = new CustomerOrder();

			o.setState("PND");
			o.setCustomerId(1);
			o.setBillingAddressId(1);
			o.setShippingAddressId(1);
			o.setTotalPrice((double) System.currentTimeMillis());

			List<OrderLine> olList = new ArrayList<OrderLine>();
			OrderLine ol = new OrderLine();
			ol.setProductId(1);
			ol.setQuantity((short) 1);
			ol.setPurchasePrice(350.00d);
			ol.setSalePrice(450.00d);
			olList.add(ol);

			ol = new OrderLine();
			ol.setProductId(45);
			ol.setQuantity((short) 1);
			ol.setPurchasePrice(350.00d);
			ol.setSalePrice(950.00d);
			olList.add(ol);

			o.setOrderLines(olList);
		}

		@Test
		void testWithValidData() {
			try {
				Long id = customerOrderService.create(o);
				CustomerOrder p = customerOrderService.findById(id, locale);
				assertEquals(id, p.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithSingleOrderLine() {
			try {
				o.getOrderLines().remove(1);
				Long id = customerOrderService.create(o);
				CustomerOrder p = customerOrderService.findById(id, locale);
				assertEquals(id, p.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testAllValidStates() {

			for (String state : new String[]{"PND", "PRS", "CAN", "DEL", "SPD"}) {
				try {
					o.setState(state);
					Long id = customerOrderService.create(o);
					CustomerOrder p = customerOrderService.findById(id, locale);
					assertEquals(id, p.getId());
				} catch (YPCException e) {
					fail(e.getMessage(), e);
				}
			}
		}

		@Test
		void testWithNoOrderLines() {
			o.setOrderLines(new ArrayList<OrderLine>());
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithNullOrderLines() {
			o.setOrderLines(null);
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithInvalidState() {
			o.setState("ABC");
			assertThrows(DataException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithNullState() {
			o.setState(null);
			assertThrows(DataException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithInvalidCustomerId() {
			o.setCustomerId(0);
			assertThrows(DataException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithInvalidBillingAddress() {
			o.setBillingAddressId(0);
			assertThrows(DataException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithInvalidShippingAddress() {
			o.setShippingAddressId(0);
			assertThrows(DataException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithNullBillingAddress() {
			o.setBillingAddressId(null);
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithNullShippingAddress() {
			o.setShippingAddressId(null);
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.create(o));
		}

		@Test
		void testWithInvalidTotalPrice() {
			o.setTotalPrice(null);
			assertThrows(IllegalArgumentException.class, () -> customerOrderService.create(o));
		}
	}

	@Test
	void testUpdate() {
		
		Locale locale = TestSuite.getLocale();
		
		try {
			CustomerOrder o = customerOrderService.findById(1l, locale);
			o.setTrackingNumber("TESTUPDATE" +System.currentTimeMillis());
			o.setState("SPD");
			assertTrue(customerOrderService.update(o));

			CustomerOrder p = customerOrderService.findById(o.getId(), locale);
			assertEquals(o.getTrackingNumber(), p.getTrackingNumber());
		} catch (YPCException e) {
			fail(e.getMessage(), e);
		}
	}

}
