package com.pinguela.yourpc.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.pinguela.yourpc.model.AbstractCriteria;

public class SQLQueryUtils {

	private static Logger logger = LogManager.getLogger(SQLQueryUtils.class);

	public static final XPathFactory XPATH_FACTORY;
	public static final Document SQL_MAPPING;

	static {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = factory.newDocumentBuilder();
			SQL_MAPPING = docBuilder.parse(SQLQueryUtils.class.getClassLoader().getResourceAsStream("sql_mapping.xml"));
			SQL_MAPPING.getDocumentElement().normalize();
			
			XPATH_FACTORY = XPathFactory.newInstance();
		} catch (ParserConfigurationException | IOException | SAXException e) {
			logger.fatal(e.getMessage(), e);
			throw new ExceptionInInitializerError(e);
		}
	}

	private static final String COLUMN_DELIMITER = ", ";
	private static final String TABLE_ALIAS_DELIMITER = ".";
	private static final String COLUMN_ALIAS_DELMITER = "_";

	private static final String AS_CLAUSE = " AS ";

	private static final String IS_NULL_CONDITION = " IS NULL";
	private static final String EQUALS_CONDITION = " = ?";
	private static final String IN_VALUES_CONDITION = " IN ()";

	private static final String ASCENDING_ORDER = " ASC";
	private static final String DESCENDING_ORDER = " DESC";

	/**
	 * Builds a comparison clause to append to a query, depending on the number of values.
	 * Structure depends on the number of values to insert:
	 * <br>
	 * If there are no values, returns an IS NULL condition,
	 * <br>
	 * if there is a single value, returns an equals condition,
	 * <br>
	 * if there are multiple values, returns an in condition.
	 * 
	 * @param size Number of values being inserted into the query
	 * @return StringBuilder object containing the clause with placeholder values
	 */
	public static StringBuilder buildPlaceholderComparisonClause(int size) {

		if (size < 1) {
			return new StringBuilder(IS_NULL_CONDITION);
		}
		if (size == 1) {
			return new StringBuilder(EQUALS_CONDITION);
		}
		return new StringBuilder(IN_VALUES_CONDITION).insert(IN_VALUES_CONDITION.length()-1, buildPlaceholderValueSequence(size));
	}

	/**
	 * Builds a comparison clause to append to a query, using PreparedStatement placeholder characters.
	 * Structure depends on the number of values to insert:
	 * <br>
	 * If there are no values, returns an IS NULL condition,
	 * <br>
	 * if there is a single value, returns an equals condition,
	 * <br>
	 * if there are multiple values, returns an in condition.
	 * 
	 * @param values Collection of values that are being inserted into the query.
	 * @return StringBuilder object containing the clause
	 */
	public static StringBuilder buildPlaceholderComparisonClause(Collection<?> values) {
		return buildPlaceholderComparisonClause(values == null ? 0 : values.size());
	}

	/** 
	 * Dynamically creates a VALUES clause to append to a SQL INSERT query containing placeholder characters.
	 * 
	 * @param rows Number of rows desired in the INSERT statement
	 * @param columns Number of columns in each row
	 * @return StringBuilder object to append to the INSERT statement
	 * @throws IllegalArgumentException if the row parameter is inferior to 1, meaning a query is being built
	 * with no rows to insert.
	 */
	public static StringBuilder buildPlaceholderValuesClause(int rows, int columns) {

		if (rows < 1) {
			throw new IllegalArgumentException("No rows to insert.");
		}

		StringBuilder values = new StringBuilder(" VALUES ");

		for (int i = 0; i<rows; i++)  {
			values.append("(").append(buildPlaceholderValueSequence(columns)).append("), ");
		}
		return values.delete(values.length()-2, values.length());
	}

	/** 
	 * Creates a VALUES clause to append to a SQL INSERT query containing placeholder characters.
	 * 
	 * @param values Collection of values to be inserted in the query
	 * @param columns Number of columns in each row
	 * @return StringBuilder object to append to the INSERT statement
	 * @throws IllegalArgumentException if the list is null or empty, meaning a query is being built
	 * with no rows to insert.
	 */
	public static StringBuilder buildPlaceholderValuesClause(Collection<?> values, int columns) {

		if (values == null || values.isEmpty()) {
			throw new IllegalArgumentException("No rows to insert.");
		}
		return buildPlaceholderValuesClause(values.size(), columns);
	}

	/**
	 * Creates a sequence of PreparedStatement placeholder characters to insert into a query.
	 * 
	 * @param size Number of placeholder characters
	 * @return StringBuilder object containing the sequence of placeholder characters of the desired length
	 */
	private static StringBuilder buildPlaceholderValueSequence(int size) {

		StringBuilder sequence = new StringBuilder();

		for (int i = 0; i<size; i++) {
			sequence.append("?, ");
		}
		return sequence.delete(sequence.length()-2, sequence.length());
	}

	/**
	 * Null-safe method that creates an SQL WHERE clause from a list of conditions.
	 * 
	 * @param conditions List of conditions to build the clause from
	 * @return StringBuilder object containing the WHERE clause.
	 */
	public static StringBuilder buildWhereClause(Collection<String> conditions) {
		return conditions == null || conditions.isEmpty()
				? new StringBuilder()
						:buildWhereClause(String.join(" AND", conditions));
	}

	/**
	 * Null-safe method that creates an SQL WHERE clause from a list of conditions.
	 * 
	 * @param conditions Conditions to build the clause from
	 * @return StringBuilder object containing the WHERE clause.
	 */
	public static StringBuilder buildWhereClause(String... conditions) {
		return conditions == null
				? new StringBuilder()
						:buildWhereClause(Arrays.asList(conditions));
	}

	/**
	 * Null-safe method that creates an SQL WHERE clause from a condition.
	 * 
	 * @param condition Condition to build the clause from
	 * @return StringBuilder object containing the WHERE clause.
	 */
	public static StringBuilder buildWhereClause(String condition) {
		return (condition == null) ? new StringBuilder() : new StringBuilder(" WHERE").append(condition);
	}

	/**
	 * Builds an ORDER BY clause to append to a query, receiving as parameters the column name 
	 * and a value indicating whether the ordering is ascendant or descendant as parameters.
	 * 
	 * @param columnName Column name that the result set is being ordered by.
	 * @param ascDesc indicates whether order should be ASC (true) or DESC (false).
	 * @return StringBuilder object containing the ORDER BY clause. If the column name is null,
	 * returns an empty StringBuilder.
	 */
	public static StringBuilder buildOrderByClause(CharSequence columnName, boolean ascDesc) {
		if (columnName == null) {
			return new StringBuilder("");
		}
		return new StringBuilder(" ORDER BY")
				.append(columnName)
				.append(ascDesc == AbstractCriteria.ASC ? ASCENDING_ORDER : DESCENDING_ORDER);
	}

	public static StringBuilder buildOrderByClause(Map<String, Boolean> orderBy, Class<?> targetClass) {
		if (orderBy == null || orderBy.isEmpty()) {
			return new StringBuilder("");
		}
		List<StringBuilder> clauses = new ArrayList<>();
		
		if (orderBy.isEmpty()) {
			orderBy.putAll(getDefaultOrder(targetClass));
		}
		
		for (String column : orderBy.keySet()) {
			String parsedColumn = getOrderColumn(targetClass, column);
			clauses.add(new StringBuilder(" ").append(parsedColumn == null ? column : parsedColumn).append(" ")
					.append(orderBy.get(column) == AbstractCriteria.ASC ? 
							ASCENDING_ORDER : DESCENDING_ORDER));
		}
		
		return new StringBuilder(" ORDER BY ").append(
				String.join(", ", clauses.toArray(new StringBuilder[clauses.size()])));
	}

	/**
	 * Builds an ORDER BY clause to append to a query, receiving a Criteria object as parameter.
	 * 
	 * @param criteria Query criteria containing the necessary information to build the clause
	 * @return StringBuilder object containing the ORDER BY clause. If any of the required 
	 * parameters are null, returns an empty StringBuilder.
	 */
	public static StringBuilder buildOrderByClause(AbstractCriteria<?> criteria, Class<?> targetClass) {

		if (criteria == null || criteria.getOrderBy() == null) {
			return new StringBuilder("");
		}
		return buildOrderByClause(criteria.getOrderBy(), targetClass);
	}
	
	public static Map<String, Boolean> getDefaultOrder(Class<?> targetClass) {
		
		Map<String, Boolean> clauses = new LinkedHashMap<String, Boolean>();
		
		String path = null;
		XPath xPath = null;

		try {
			path = String.format("//mapping[@queryType='native' and ./entity='%1$s']/orderMapping[@default = 'true']", targetClass.getName());
			xPath = XPATH_FACTORY.newXPath();
			
			NodeList nodeList = (NodeList) xPath.compile(path).evaluate(SQL_MAPPING, XPathConstants.NODESET);
			
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				Node orderAttribute = node.getAttributes().getNamedItem("order");
				boolean ascDesc = orderAttribute == null ? AbstractCriteria.ASC :
							"asc".equals(orderAttribute.getNodeValue());
				
				xPath = XPATH_FACTORY.newXPath();
				path = "./column/text()";
				
				Node columnNode = (Node) xPath.compile(path).evaluate(node, XPathConstants.NODE);
				clauses.put(columnNode.getNodeValue(), ascDesc);
			}
		} catch (XPathExpressionException e) {
			logger.error("Could not find node using XPath {}", path);
			throw new IllegalArgumentException(e.getMessage(), e);
		}
		
		return clauses;	
	}

	private static String getOrderColumn(Class<?> targetClass, String key) {

		String path = String.format("//mapping[queryType='native' and ./entity='%1$s']/orderMapping[./key='%2$s']/column/text()",
				targetClass.getName(), key);
		XPath xPath = XPATH_FACTORY.newXPath();
		
		try {
			Node node = (Node) xPath.compile(path).evaluate(SQL_MAPPING, XPathConstants.NODE);
			return node == null ? null : node.getNodeValue();
		} catch (XPathExpressionException e) {
			logger.error("Could not find node using XPath {}", path);
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * Converts the received String value to uppercase and wraps it with '%' wildcards for use in a LIKE clause.
	 * 
	 * @param str The string value to format
	 * @return The formatted string, suitable for use in a LIKE clause    
	 */
	public static String wrapLike(String str) {
		return new StringBuilder().append("%").append(str.toUpperCase()).append("%").toString();
	}

	public static String applyTableAlias(String alias, String columnName) {
		return String.join(TABLE_ALIAS_DELIMITER, alias, columnName);
	}

	public static String generateColumnAlias(Class<?> entity, String column) {
		String columnSubstring = column.replace(TABLE_ALIAS_DELIMITER, COLUMN_ALIAS_DELMITER);
		return String.join(COLUMN_ALIAS_DELMITER, entity.getSimpleName(), columnSubstring == null ? column : columnSubstring);
	}

	public static Map<String, String> generateColumnAliases(Class<?> entity, Collection<String> columnNames) {
		Map<String, String> columns = new LinkedHashMap<>();
		Iterator<String> iterator = columnNames.iterator();

		while (iterator.hasNext()) {
			String column = iterator.next();
			columns.put(column, generateColumnAlias(entity, column));
		}
		return Collections.unmodifiableMap(columns);
	}

	public static String createColumnClause(Map<String, String> columnNamesAndAliases) {
		String[] clauses = new String[columnNamesAndAliases.size()];
		Iterator<String> iterator = columnNamesAndAliases.keySet().iterator();

		for (int i = 0; i < clauses.length; i++) {
			String column = iterator.next();
			clauses[i] = String.join(AS_CLAUSE, column, columnNamesAndAliases.get(column));
		}

		return String.join(COLUMN_DELIMITER, clauses);
	}

}
