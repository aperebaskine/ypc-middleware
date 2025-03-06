package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.pinguela.DataException;
import com.pinguela.InvalidLoginCredentialsException;
import com.pinguela.ServiceException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.service.impl.CustomerServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class CustomerServiceTest {

	private CustomerService customerService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		customerService = new CustomerServiceImpl();
	}

	@Nested
	class TestLogin {

		@Test
		void testWithInvalidEmail() {
			assertThrows(InvalidLoginCredentialsException.class, () -> customerService.login("a@a.com", "abc123."));
		}

		@Test
		void testWithInvalidPassword() {
			assertThrows(InvalidLoginCredentialsException.class, () -> customerService.login("ethan@example.com", "hunter2"));
		}

		@Test
		void testWithValidCredentials() {

			try {
				String sessionToken = customerService.login("ethan@example.com", "abc123.");
				Customer c = customerService.findBySessionToken(sessionToken);
				assertEquals("ethan@example.com", c.getEmail());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestRegister {

		private Customer c;

		@BeforeEach
		void setUp() {

			initCustomer();
		}
		
		private void initCustomer() {
			c = new Customer();
			c.setFirstName("Alexandre");
			c.setLastName1("Perebaskine");
			c.setDocumentTypeId("NIE");
			c.setDocumentNumber("X9876543V");
			c.setPhoneNumber("TEST" +System.currentTimeMillis());
			c.setEmail(System.currentTimeMillis() +"@gmail.com");
			c.setUnencryptedPassword("abc123.");
		}

		@Test
		void testWithValidData() {

			try {
				Integer id = customerService.register(c);
				Customer d = customerService.findById(id);
				assertEquals(c.getPhoneNumber(), d.getPhoneNumber());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testValidDocumentTypes() {

			for (String documentType : new String[] {"NIF", "FOR", "PPT"}) {
				try {
					initCustomer();
					c.setDocumentTypeId(documentType);
					
					Integer id = customerService.register(c);
					Customer d = customerService.findById(id);
					assertEquals(c.getPhoneNumber(), d.getPhoneNumber());
					assertEquals(c.getDocumentTypeId(), d.getDocumentTypeId());
				} catch (YPCException e) {
					fail(e.getMessage(), e);
				}
			}
		}
		
		@Test
		void testWithNullCustomer() {
			assertThrows(IllegalArgumentException.class, () -> customerService.register(null));
		}

		@Test
		void testWithInvalidFirstName() {
			c.setFirstName(null);
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithInvalidLastName() {
			c.setLastName1(null);
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithInvalidDocumentType() {
			c.setDocumentTypeId("ABC");
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithNullDocumentType() {
			c.setDocumentTypeId(null);
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithInvalidDocumentNumber() {
			c.setDocumentNumber(null);
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithInvalidPhoneNumber() {
			c.setPhoneNumber(null);
			assertThrows(DataException.class, () -> customerService.register(c));
		}

		@Test
		void testWithInvalidEmail() {
			c.setEmail("aacom");
			assertThrows(DataException.class, () -> customerService.register(c));
		}
		
		@Test
		void testWithNonUniqueEmail() {
			c.setEmail("ethan@example.com");
			assertThrows(DataException.class, () -> customerService.register(c));
		}
	}

	@Nested
	class TestFindById {

		@Test
		void testWithValidId() {
			try {
				Customer c = customerService.findById(1);
				assertEquals(1, c.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				Customer c = customerService.findById(0);
				assertNull(c);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				Customer c = customerService.findById(null);
				assertNull(c);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestFindByEmail {

		@Test
		void testWithValidEmail() {
			try {
				Customer c = customerService.findByEmail("cant@see.me");
				assertEquals("cant@see.me", c.getEmail());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidEmail() {
			try {
				Customer c = customerService.findByEmail("cantseeme");
				assertNull(c);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullEmail() {
			try {
				Customer c = customerService.findByEmail(null);
				assertNull(c);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestFindBy {

		private CustomerCriteria criteria;

		@BeforeEach
		void setUp() {
			criteria = new CustomerCriteria();
		}

		@Test
		void testFindByEmptyCriteria() {
			try {
				List<Customer> customers = customerService.findBy(criteria);
				assertTrue(customers.size() >= 6);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByFirstName() {
			try {
				criteria.setFirstName("John");
				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getFirstName(), c.getFirstName());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByLastName1() {
			try {
				criteria.setLastName1("Cena");
				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getLastName1(), c.getLastName1());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByLastName2() {
			try {
				criteria.setLastName2("López");
				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getLastName2(), c.getLastName2());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByDocumentNumber() {
			try {
				criteria.setDocumentNumber("IJ123456");
				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getDocumentNumber(), c.getDocumentNumber());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByPhoneNumber() {
			try {
				criteria.setPhoneNumber("123456789");
				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getPhoneNumber(), c.getPhoneNumber());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testFindByMultipleCriteria() {
			try {
				criteria.setFirstName("John");
				criteria.setLastName1("Cena");
				criteria.setEmail("cant@see.me");

				List<Customer> customers = customerService.findBy(criteria);
				assertFalse(customers.isEmpty());

				for (Customer c : customers) {
					assertEquals(criteria.getFirstName(), c.getFirstName());
					assertEquals(criteria.getLastName1(), c.getLastName1());
					assertEquals(criteria.getEmail(), c.getEmail());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

	}

	@Test
	void testUpdate() {
		try {
			Customer c = customerService.findById(1);
			c.setLastName2("TEST" +System.currentTimeMillis());
			assertTrue(customerService.update(c));

			Customer d = customerService.findById(1);
			assertEquals(c.getLastName2(), d.getLastName2());
		} catch (YPCException e) {
			fail(e.getMessage(), e);
		}
	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class TestUpdatePassword {

		@AfterAll
		void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}

		@Test
		void testWithValidData() {
			String newPassword = Long.valueOf(System.currentTimeMillis()).toString();

			try {
				assertTrue(customerService.updatePassword(1, newPassword));
				assertNotNull(customerService.login("ethan@example.com", newPassword));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			String newPassword = Long.valueOf(System.currentTimeMillis()).toString();
			assertThrows(DataException.class, () -> customerService.updatePassword(0, newPassword));
		}

		@Test
		void testWithInvalidPassword() {
			assertThrows(DataException.class, () -> customerService.updatePassword(1, null));
		}
	}

	@Nested
	@TestInstance(Lifecycle.PER_CLASS)
	class TestDelete {

		@AfterAll
		void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}

		@Test
		void testDeleteWithValidId() {
			try {
				assertTrue(customerService.delete(1));
				assertNull(customerService.findById(1));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testDeleteWithInvalidId() {
			try {
				assertFalse(customerService.delete(0));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testDeleteWithNullId() {
			assertThrows(ServiceException.class, () -> customerService.delete(null));
		}
	}

}
