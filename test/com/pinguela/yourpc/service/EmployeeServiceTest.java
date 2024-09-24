package com.pinguela.yourpc.service;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import com.pinguela.YPCException;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.model.FullName;
import com.pinguela.yourpc.model.ID;
import com.pinguela.yourpc.model.IDType;
import com.pinguela.yourpc.service.impl.EmployeeServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
class EmployeeServiceTest {
	
	private EmployeeService employeeService;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		employeeService = new EmployeeServiceImpl();
	}

	@Nested
	class TestLogin {
		
		@Test
		void testWithInvalidUsername() {
			assertThrows(InvalidLoginCredentialsException.class, () -> employeeService.login("notanuser", "abc123."));
		}
		
		@Test
		void testWithInvalidPassword() {
			assertThrows(InvalidLoginCredentialsException.class, () -> employeeService.login("aperebaskine", "hunter2"));
		}
		
		@Test
		void testWithValidCredentials() {
			
			try {
				Employee e = employeeService.login("aperebaskine", "abc123.");
				assertEquals("aperebaskine", e.getUsername());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}
	
	@Nested
	class TestRegister {
		
		private Employee e;
		
		@BeforeEach
		void setUp() {
			initEmployee();
		}
		
		private void initEmployee() {
			
			e = new Employee();
			e.setName(new FullName("Alexandre", "Perebaskine", null));
			e.setDocument(new ID(new IDType("NIE", null), "X9876543V"));
			e.setPhoneNumber("TEST" +System.currentTimeMillis());
			e.setEmail(System.currentTimeMillis() +"@gmail.com");
			e.setUsername("TEST" +System.currentTimeMillis());
			e.setUnencryptedPassword("abc123.");
			e.setIban("QWERTYUIOPASDFGHJKLÑ");
			e.setBic("QWERTYUIOPA");
			
			Address a = new Address();
			a.setStreetName("TEST" +System.currentTimeMillis());
			a.setStreetNumber((short) 1);
			a.setFloor((short) 1);
			a.setDoor("D");
			a.setZipCode("12345");
			
			City city = new City();
			city.setId(1);
			a.setCity(city);
			
			e.setAddress(a);
		}
		
		@Test
		void testWithValidData() {
			
			try {
				Integer id = employeeService.register(e);
				Employee f = employeeService.findById(id);
				assertEquals(e.getUsername(), f.getUsername());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testAllValidDocumentTypes() {

			for (String documentType : new String[] {"NIE", "NIF", "FOR", "PPT"}) {
				try {
					initEmployee();
					e.getDocument().getType().setId(documentType);
					Integer id = employeeService.register(e);
					Employee f = employeeService.findById(id);
					assertEquals(e.getPhoneNumber(), f.getPhoneNumber());
				} catch (YPCException e) {
					fail(e.getMessage(), e);
				}
			}
		}
		
		@Test
		void testWithInvalidFirstName() {
			e.getName().setFirstName(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidLastName() {
			e.getName().setLastName1(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidDocumentType() {
			e.setDocumentTypeId("ABC");
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithNullDocumentType() {
			e.setDocumentTypeId(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidDocumentNumber() {
			e.setDocumentNumber(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidPhoneNumber() {
			e.setPhoneNumber(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidEmail() {
			e.setEmail("aacom");
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithNonUniqueEmail() {
			e.setEmail("olivia@example.com");
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidUsername() {
			e.setUsername(null);
			assertThrows(IllegalArgumentException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidIban() {
			e.setIban(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
		
		@Test
		void testWithInvalidBic() {
			e.setBic(null);
			assertThrows(DataException.class, () -> employeeService.register(e));
		}
	}
	
	@Nested
	class TestFindById {
		
		@Test
		void testWithValidId() {
			try {
				Employee e = employeeService.findById(1);
				assertEquals(1, e.getId());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithInvalidId() {
			try {
				Employee e = employeeService.findById(0);
				assertNull(e);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithNullId() {
			try {
				Employee e = employeeService.findById(null);
				assertNull(e);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}
	
	@Nested
	class TestFindByUsername {
		
		@Test
		void testWithValidUsername() {
			try {
				Employee e = employeeService.findByUsername("rastley");
				assertEquals("rastley", e.getUsername());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithInvalidUsername() {
			try {
				Employee e = employeeService.findByUsername("notanemployee");
				assertNull(e);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithNullUsername() {
			try {
				Employee e = employeeService.findByUsername(null);
				assertNull(e);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Nested
	class TestFindBy {
		
		private EmployeeCriteria criteria;
		
		@BeforeEach
		void setUp() {
			criteria = new EmployeeCriteria();
		}
		
		void testWithNullCriteria() {
			assertThrows(IllegalArgumentException.class, () -> employeeService.findBy(null));
		}
		
		@Test
		void testFindByEmptyCriteria() {
			try {
				List<Employee> employees = employeeService.findBy(criteria);
				assertTrue(employees.size() >= 6);
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByFirstName() {
			try {
				criteria.setFirstName("Rick");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getFirstName(), e.getFirstName());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByLastName1() {
			try {
				criteria.setLastName1("Astley");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getLastName1(), e.getLastName1());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByLastName2() {
			try {
				criteria.setLastName2("Rodriguez");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getLastName2(), e.getLastName2());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByDocumentNumber() {
			try {
				criteria.setDocumentNumber("87654321G");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getDocumentNumber(), e.getDocumentNumber());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByPhoneNumber() {
			try {
				criteria.setPhoneNumber("678901234");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getPhoneNumber(), e.getPhoneNumber());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByEmail() {
			try {
				criteria.setEmail("olivia@example.com");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getEmail(), e.getEmail());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByDepartmentId() {
			try {
				criteria.setDepartmentId("FIN");
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testFindByMultipleCriteria() {
			try {
				criteria.setDepartmentId("FIN");
				criteria.setPhoneNumber("666666666");
				
				List<Employee> employees = employeeService.findBy(criteria);
				assertFalse(employees.isEmpty());
				
				for (Employee e : employees) {
					assertEquals(criteria.getPhoneNumber(), e.getPhoneNumber());
				}
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

	@Test
	void testUpdate() {
		try {
			Employee e = employeeService.findById(1);
			e.setLastName2("TEST" +System.currentTimeMillis());
			assertTrue(employeeService.update(e));
			
			Employee f = employeeService.findById(1);
			assertEquals(e.getLastName2(), f.getLastName2());
		} catch (YPCException e) {
			fail(e.getMessage(), e);
		}
	}
	
	@Nested
	class TestUpdatePassword {
		
		@AfterAll
		static void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
		
		@Test
		void testWithValidData() {
			String newPassword = Long.valueOf(System.currentTimeMillis()).toString();
			
			try {
				assertTrue(employeeService.updatePassword(1, newPassword));
				assertNotNull(employeeService.login("aperebaskine", newPassword));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testWithInvalidId() {
			String newPassword = Long.valueOf(System.currentTimeMillis()).toString();
			assertThrows(DataException.class, () -> employeeService.updatePassword(0, newPassword));
		}
		
		@Test
		void testWithInvalidPassword() {
			assertThrows(DataException.class, () -> employeeService.updatePassword(1, null));
		}
	}
	
	@Nested
	class TestDelete {
		
		@AfterAll
		static void tearDownAfterClass() throws Exception {
			TestSuite.initializeTransactionTables();
		}
		
		@Test
		void testDeleteWithValidId() {
			try {
				assertTrue(employeeService.delete(1));
				assertNull(employeeService.findById(1));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
		
		@Test
		void testDeleteWithInvalidId() {
			try {
				assertFalse(employeeService.delete(0));
			} catch (YPCException e) {
				fail(e.getMessage(), e);
			}
		}
	}

}
