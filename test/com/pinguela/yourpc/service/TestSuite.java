package com.pinguela.yourpc.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import com.pinguela.yourpc.util.JDBCUtils;

@Suite
@SelectClasses({AddressServiceTest.class,
	AttributeServiceTest.class,
	CategoryServiceTest.class,
	CityServiceTest.class,
	CountryServiceTest.class,
	CustomerOrderServiceTest.class,
	CustomerServiceTest.class,
	EmployeeServiceTest.class,
//	MailServiceTest.class,
	ProductServiceTest.class,
	ProvinceServiceTest.class})
public class TestSuite {
	
	private static final Locale LOCALE = Locale.forLanguageTag("en-GB");

	private static final Map<String, Integer> TABLE_EXPECTED_ROW_COUNTS;
	
	private static final String SQL_RESOURCE_PATH = "test/sql/";
	private static final String[] SQL_FILES = {
		"create.sql",
		"constraints.sql",
		"populate_master_tables.sql",
		"populate_transaction_tables.sql"
	};
	
	static {
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("ADDRESS", 17);
		map.put("ATTRIBUTE_DATA_TYPE", 4);
		map.put("ATTRIBUTE_TYPE", 56);
		map.put("ATTRIBUTE_VALUE", 290);
		map.put("CATEGORY", 17);
		map.put("CATEGORY_ATTRIBUTE_TYPE", 71);
		map.put("CITY", 80);
		map.put("COUNTRY", 27);
		map.put("CUSTOMER", 7);
		map.put("CUSTOMER_ORDER", 23);
		map.put("DEPARTMENT", 7);
		map.put("DOCUMENT_TYPE", 4);
		map.put("EMPLOYEE", 7);
		map.put("EMPLOYEE_DEPARTMENT", 9);
		map.put("ORDER_LINE", 46);
		map.put("ORDER_STATE", 5);
		map.put("PRODUCT", 55);
		map.put("PRODUCT_ATTRIBUTE_VALUE", 521);
		map.put("PROVINCE", 90);
		map.put("RMA", 4);
		map.put("RMA_ORDER_LINE", 5);
		map.put("RMA_STATE", 4);
		map.put("TICKET", 6);
		map.put("TICKET_MESSAGE", 32);
		map.put("TICKET_ORDER_LINE", 5);
		map.put("TICKET_STATE", 4);
		map.put("TICKET_TYPE", 4);
		
		TABLE_EXPECTED_ROW_COUNTS = Collections.unmodifiableMap(map);
	}

	@BeforeAll
	static void initializeTestDatabase() throws Exception {
		executeSqlScripts(SQL_FILES);
	}

	@Test
	void validateDatabase() throws Exception {
		
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = conn.createStatement();
		
		for (String tableName : TABLE_EXPECTED_ROW_COUNTS.keySet()) {
			ResultSet rs = stmt.executeQuery(String.format("SELECT COUNT(*) FROM %s", tableName));
			int resultCount = -1;
			
			if (rs.next()) {
				resultCount = rs.getInt(1);
			}

			assertEquals(TABLE_EXPECTED_ROW_COUNTS.get(tableName), resultCount);
		}
	}
	
	/**
	 * Convenience method provided for test cases to reset the transaction tables only, for performance purposes.
	 * @throws Exception
	 */
	static final void initializeTransactionTables() throws Exception {
		executeSqlScripts(SQL_FILES[3]);
	}
	
	private static final void executeSqlScripts(String... sqlFileNames) throws Exception {
		
		Connection conn = JDBCUtils.getConnection();
		Statement stmt = conn.createStatement();
		
		ClassLoader classLoader = TestSuite.class.getClassLoader();
		
		for (String sqlFile : sqlFileNames) {
			
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(
					classLoader.getResourceAsStream(SQL_RESOURCE_PATH.concat(sqlFile))))) {
				
				String readLine = processNextLine(reader);
				while (readLine != null) {
					
					StringBuilder query = new StringBuilder();

					do {
						query.append("\n").append(readLine);
						readLine = processNextLine(reader);
					} while (query.charAt(query.length() - 1) != ';');
					
					stmt.addBatch(query.toString());
				}
			}
		}
		stmt.executeBatch();
	}
	
	private static final String processNextLine(BufferedReader reader) throws Exception {
		
		String readLine = reader.readLine();
		
		if (readLine != null) {
			readLine = readLine.trim();
			
			int indexOfCommentStart = readLine.indexOf("-- ");
			if (indexOfCommentStart > -1) {
				readLine = readLine.substring(0, indexOfCommentStart).trim();
			}
			
			if (readLine.isEmpty()) {
				readLine = processNextLine(reader);
			}
		}
		
		return readLine;
	}
	
	public static Locale getLocale() {
		return LOCALE;
	}
	
}
