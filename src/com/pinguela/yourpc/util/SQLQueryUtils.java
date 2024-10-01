package com.pinguela.yourpc.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.pinguela.yourpc.model.AbstractCriteria;

public class SQLQueryUtils {

	private static final String COLUMN_DELIMITER = ", ";
	private static final String TABLE_ALIAS_DELIMITER = ".";
	private static final String COLUMN_ALIAS_DELMITER = "_";

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

	public static StringBuilder buildOrderByClause(Map<String, Boolean> orderBy) {
		if (orderBy == null || orderBy.isEmpty()) {
			return new StringBuilder("");
		}
		List<StringBuilder> clauses = new ArrayList<>();
		for (String column : orderBy.keySet()) {
			clauses.add(new StringBuilder(" ").append(column).append(" ")
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
	public static StringBuilder buildOrderByClause(AbstractCriteria<?> criteria) {

		if (criteria == null || criteria.getOrderBy() == null) {
			return new StringBuilder("");
		}
		return buildOrderByClause(criteria.getOrderBy());
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

	public static String applyAlias(String tableAlias, String columnName) {
//		return String.format(
//				"%1$s%2$s%3$s AS %1$s%4$s%3$s", 
//				tableAlias,
//				TABLE_ALIAS_DELIMITER,
//				columnName,
//				COLUMN_ALIAS_DELMITER
//				);
		return String.join(TABLE_ALIAS_DELIMITER, tableAlias, columnName);
	}

	public static String[] applyAlias(String alias, String... columnNames) {
		String[] columnNamesWithAlias = new String[columnNames.length];

		for (int i = 0; i < columnNames.length; i++) {
			columnNamesWithAlias[i] = applyAlias(alias, columnNames[i]);
		}

		return columnNamesWithAlias;
	}

	public static String getColumnClause(String... columnNames) {
		return String.join(COLUMN_DELIMITER, columnNames);
	}

	public static String getColumnClause(String[]... columnNames) {
		String[] clauses = new String[columnNames.length];
		for (int i = 0; i < columnNames.length; i++) {
			clauses[i] = getColumnClause(columnNames[i]);
		}
		return String.join(COLUMN_DELIMITER, clauses);
	}

}
