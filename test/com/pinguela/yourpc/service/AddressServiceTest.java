package com.pinguela.yourpc.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
import com.pinguela.ServiceException;
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.service.impl.AddressServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class AddressServiceTest {

	private AddressService addressService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		addressService = new AddressServiceImpl();
	}

	@Nested
	class TestFindById {

		@Test
		void testWithValidId() {
			try {
				Address a = addressService.findById(1);
				assertEquals(1, a.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				Address a = addressService.findById(0);
				assertNull(a);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				Address a = addressService.findById(null);
				assertNull(a);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

	}

	@Nested
	class TestFindByEmployee {

		@Test
		void testWithValidEmployeeId() {
			try {
				Address a = addressService.findByEmployee(1);
				assertEquals(1, a.getEmployeeId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidEmployeeId() {
			try {
				Address a = addressService.findByEmployee(0);
				assertNull(a);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullEmployeeId() {
			try {
				Address a = addressService.findByEmployee(null);
				assertNull(a);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

	}

	@Nested
	class TestFindByCustomer {

		@Test
		void testWithValidCustomerId() {
			try {
				List<Address> list = addressService.findByCustomer(1);
				assertFalse(list.isEmpty());
				for (Address a : list) {
					assertEquals(1, a.getCustomerId());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidCustomerId() {
			try {
				List<Address> list = addressService.findByCustomer(0);
				assertTrue(list.isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullCustomerId() {
			try {
				List<Address> a = addressService.findByCustomer(null);
				assertNull(a);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestCreate {

		private Address a;
		private Customer c;
		private Employee e;
		private City ci;

		@BeforeEach
		void setUp() throws Exception {
			a = new Address();
			c = new Customer();
			e = new Employee();

			a.setStreetName("TEST" + System.currentTimeMillis());
			a.setZipCode("1");
			
			c.setId(1);
			a.setCustomer(c);
			
			ci.setId(1);
			a.setCity(ci);
			
			a.setDefault(false);
			a.setBilling(false);
			
			e.setId(1);
		}

		@Test
		void testWithValidCustomerData() {

			try {
				addressService.create(a);
				Address b = addressService.findById(a.getId());
				assertEquals(a.getStreetName(), b.getStreetName());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			} 
		}

		@Test
		void testWithValidEmployeeData() {

			a.setCustomer(null);
			a.setEmployee(e);

			try {
				addressService.create(a);
				Address b = addressService.findById(a.getId());
				assertEquals(a.getStreetName(), b.getStreetName());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			} 
		}
		
		@Test
		void testWithNullAddress() {
			assertThrows(ServiceException.class, () -> addressService.create(null));
		}

		@Test
		void testWithNullStreetName() {
			a.setStreetName(null);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithNullZipCode() {
			a.setZipCode(null);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithNonNullCustomerAndEmployeeId() {
			a.setEmployee(e);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithNullCustomerAndEmployeeId() {
			a.getCustomer().setId(null);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithInvalidCustomerId() {
			a.getCustomer().setId(0);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithInvalidEmployeeId() {
			a.setCustomer(null);
			e.setId(0);
			a.setEmployee(e);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testWithInvalidCityId() {
			a.getCity().setId(0);
			assertThrows(DataException.class, () -> addressService.create(a));
		}

		@Test
		void testCreateNewDefaultAddress() {
			try {
				a.setDefault(true);
				addressService.create(a);

				List<Address> list = addressService.findByCustomer(a.getCustomerId());
				assertTrue(list.size() > 1);

				int count = 0;
				for (Address address : list) {
					if (address.isDefault()) {
						count++;
					}
				}
				assertEquals(1, count);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testCreateNewBillingAddress() {

			try {
				a.setBilling(true);
				addressService.create(a);

				List<Address> list = addressService.findByCustomer(a.getCustomerId());
				assertTrue(list.size() > 1);

				int count = 0;
				for (Address address : list) {
					if (address.isBilling()) {
						count++;
					}
				}
				assertEquals(1, count);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestUpdate {

		@Test
		void testWithNullAddress() {
			assertThrows(ServiceException.class, () -> addressService.update(null));
		}

		@Test
		void testUpdateAddressAssignedToOrder() {
			try {
				Address a = addressService.findById(1);
				a.setStreetName("TESTUPDATE" +System.currentTimeMillis());

				int newId = addressService.update(a);
				assertNotEquals(1, newId);

				Address b = addressService.findById(newId);
				assertEquals(a.getStreetName(), b.getStreetName());				
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testUpdateAddressNotAssignedToOrder() {
			try {
				Address a = addressService.findById(8);
				a.setStreetName("TESTUPDATE" +System.currentTimeMillis());

				int newId = addressService.update(a);
				assertEquals(a.getId(), newId);

				Address b = addressService.findById(a.getId());
				assertEquals(a.getStreetName(), b.getStreetName());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}

		}
		
		@Test
		void testUpdateBillingAddress() {
			try {
				Address a = addressService.findById(9);
				assertFalse(a.isBilling());
				a.setBilling(true);
				
				int newId = addressService.update(a);
				assertEquals(9, newId);
				
				List<Address> list = addressService.findByCustomer(a.getCustomerId());
				
				assertTrue(list.size() > 1);
				for (Address address : list) {
					assertTrue((address.getId() == 9 && address.isBilling()) || !address.isBilling());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testUpdateDefaultAddress() {
			try {
				Address a = addressService.findById(10);
				assertFalse(a.isDefault());
				a.setDefault(true);
				
				int newId = addressService.update(a);
				assertEquals(10, newId);
				
				List<Address> list = addressService.findByCustomer(a.getCustomerId());
				
				assertTrue(list.size() > 1);
				for (Address address : list) {
					assertTrue((address.getId() == 10 && address.isDefault()) || !address.isDefault());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@AfterAll
		static void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
	}

	@Nested
	class TestDelete {

		@Test
		void testWithValidId() {
			try {
				assertTrue(addressService.delete(2));
				assertNull(addressService.findById(2));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidId() {
			try {
				assertFalse(addressService.delete(0));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullId() {
			try {
				assertFalse(addressService.delete(null));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@AfterAll
		static void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
	}

	@Nested
	class TestDeleteByCustomer {

		@Test
		void testWithValidCustomerId() {
			try {
				assertTrue(addressService.deleteByCustomer(1));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithInvalidCustomerId() {
			try {
				assertFalse(addressService.deleteByCustomer(0));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@Test
		void testWithNullCustomerId() {
			try {
				assertFalse(addressService.deleteByCustomer(null));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}

		@AfterAll
		static void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
	}

}
