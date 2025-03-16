package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.model.dto.AbstractProductDTO;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.model.dto.AttributeValueDTO;
import com.pinguela.yourpc.model.dto.CategoryDTO;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class AttributeDAOImpl implements AttributeDAO {

	private static final String DATA_TYPE_COLUMN = "ATTRIBUTE_DATA_TYPE_ID";
	private static final String NAME_COLUMN = "NAME";
	private static final String ID_COLUMN = "ID";
	private static final String VALUE_ID_ALIAS = "VALUE_ID";


	private static final String SELECT_ID = 
			" SELECT av." +ID_COLUMN;
	private static final String SELECT_COLUMNS;
	static {
		StringBuilder selectClause = new StringBuilder(" SELECT at.").append(DATA_TYPE_COLUMN)
				.append(", at.").append(ID_COLUMN)
				.append(", atl.").append(NAME_COLUMN)
				.append(", av.").append(ID_COLUMN).append(" AS ").append(VALUE_ID_ALIAS);

		for (String dataType : AttributeDTO.TYPE_PARAMETER_CLASSES.keySet()) {
			selectClause.append(", av.").append(AttributeUtils.getValueColumnName(dataType));
		}
		SELECT_COLUMNS = selectClause.toString();
	}

	private static final String FROM_TABLE = 
			" FROM ATTRIBUTE_TYPE at"
					+ " LEFT JOIN ATTRIBUTE_VALUE av"
					+ " ON av.ATTRIBUTE_TYPE_ID = at.ID";
	private static final String JOIN_LOCALE =
			" INNER JOIN ATTRIBUTE_TYPE_LOCALE atl"
					+ " ON at.ID = atl.ATTRIBUTE_TYPE_ID"
					+ " AND atl.LOCALE_ID = ?";
	private static final String JOIN_CATEGORY_ATTRIBUTE_TYPE_PLACEHOLDER = 
			" INNER JOIN CATEGORY_ATTRIBUTE_TYPE cat"
					+ " ON cat.ATTRIBUTE_TYPE_ID = at.ID"
					+ " AND cat.CATEGORY_ID";
	private static final String JOIN_PRODUCT_ATTRIBUTE_VALUE = 
			" INNER JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.ATTRIBUTE_VALUE_ID = av." +ID_COLUMN;
	private static final String ON_PRODUCT_ID = " AND pav.PRODUCT_ID = ?";
	private static final String JOIN_PRODUCT_AND_CATEGORY_PLACEHOLDER = 
			" INNER JOIN PRODUCT p"
					+ " ON p.ID = pav.PRODUCT_ID"
					+ " AND p.DISCONTINUATION_DATE IS NULL"
					+ " INNER JOIN CATEGORY c"
					+ " ON c.ID = p.CATEGORY_ID"
					+ " AND c.ID";
	private static final String WHERE_PLACEHOLDER_VALUE_AND_NAME = 
			" WHERE av.%1$s = ?" +" AND at.ID = ?";
	private static final String GROUP_BY_VALUE = " GROUP BY at.ID, atl.ID, av.ID";
	private static final String ORDER_BY_CLAUSE = 
			" ORDER BY atl.NAME ASC, av.VALUE_VARCHAR ASC, av.VALUE_BIGINT ASC, av.VALUE_DECIMAL ASC, av.VALUE_BOOLEAN ASC";

	private static final String FINDBY_QUERY = SELECT_COLUMNS +FROM_TABLE +JOIN_LOCALE;
	private static final String FINDBYPRODUCT_QUERY = FINDBY_QUERY +JOIN_PRODUCT_ATTRIBUTE_VALUE +ON_PRODUCT_ID +ORDER_BY_CLAUSE;
	private static final String FINDIDBYVALUE_QUERY = SELECT_ID +FROM_TABLE +WHERE_PLACEHOLDER_VALUE_AND_NAME;

	private static final String ASSIGN_QUERY =
			" INSERT INTO PRODUCT_ATTRIBUTE_VALUE(PRODUCT_ID, ATTRIBUTE_VALUE_ID)";
	private static final int ASSIGN_QUERY_COLUMN_COUNT = 2;

	private static final String UNASSIGN_QUERY = 
			" DELETE FROM PRODUCT_ATTRIBUTE_VALUE WHERE PRODUCT_ID = ?";

	// Value column name is a placeholder to be set by the method
	private static final String CREATE_IF_ABSENT_QUERY =
			" INSERT INTO ATTRIBUTE_VALUE(ATTRIBUTE_TYPE_ID, %1$s)"
					+ " VALUES (?, ?)";

	private static Logger logger = LogManager.getLogger(AttributeDAOImpl.class);

	@Override
	public AttributeDTO<?> findById(Connection conn, Integer id, Locale locale, boolean returnUnassigned, Short categoryId)
			throws DataException {

		Map<Short, CategoryDTO> lowerHierarchy = CategoryUtils.getLowerHierarchy(categoryId);
		
		if (categoryId != null && lowerHierarchy.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder(FINDBY_QUERY);
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			query.append(JOIN_PRODUCT_ATTRIBUTE_VALUE);
			if (categoryId != null) {
				query.append(JOIN_PRODUCT_AND_CATEGORY_PLACEHOLDER)
				.append(SQLQueryUtils.buildPlaceholderComparisonClause(lowerHierarchy.keySet()));
			}
		}
		query.append(" WHERE at.ID = ?");
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			query.append(GROUP_BY_VALUE);
		}
		query.append(ORDER_BY_CLAUSE);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					query.toString(), 
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY
					);
			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			if (categoryId != null) {
				for (CategoryDTO dto : lowerHierarchy.values()) {
					stmt.setShort(i++, dto.getId());
				}
			}
			stmt.setInt(i++, id);

			rs = stmt.executeQuery();
			Collection<AttributeDTO<?>> results = loadResults(rs).values();
			Iterator<AttributeDTO<?>> iterator = results.iterator();

			return iterator.hasNext() ? iterator.next() : null;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public AttributeDTO<?> findByName(Connection conn, String name, Locale locale, boolean returnUnassigned, Short categoryId) throws DataException {

		Map<Short, CategoryDTO> lowerHierarchy = CategoryUtils.getLowerHierarchy(categoryId);
		
		if (categoryId != null && lowerHierarchy.isEmpty()) {
			return null;
		}

		StringBuilder query = new StringBuilder(FINDBY_QUERY);
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			query.append(JOIN_PRODUCT_ATTRIBUTE_VALUE);
			if (categoryId != null) {
				query.append(JOIN_PRODUCT_AND_CATEGORY_PLACEHOLDER)
				.append(SQLQueryUtils.buildPlaceholderComparisonClause(lowerHierarchy.keySet()));
			}
		}
		query.append(" WHERE atl.NAME = ?");
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			query.append(GROUP_BY_VALUE);
		}
		query.append(ORDER_BY_CLAUSE);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					query.toString(), 
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY
					);
			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			if (categoryId != null) {
				for (CategoryDTO dto : lowerHierarchy.values()) {
					stmt.setShort(i++, dto.getId());
				}
			}
			stmt.setString(i++, name);

			rs = stmt.executeQuery();
			return loadResults(rs).get(name);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Map<String, AttributeDTO<?>> findByCategory(Connection conn, Short categoryId, Locale locale, boolean returnUnassigned)
			throws DataException {

		Map<Short, CategoryDTO> upperHierarchy = CategoryUtils.getUpperHierarchy(categoryId);
		Map<Short, CategoryDTO> lowerHierarchy = CategoryUtils.getLowerHierarchy(categoryId);
		
		if (upperHierarchy.isEmpty() || lowerHierarchy.isEmpty()) {
			return Collections.emptyMap();
		}

		StringBuilder query = new StringBuilder(FINDBY_QUERY);
		query.append(JOIN_CATEGORY_ATTRIBUTE_TYPE_PLACEHOLDER)
		.append(SQLQueryUtils.buildPlaceholderComparisonClause(upperHierarchy.keySet()));
		if (returnUnassigned == AttributeService.NO_UNASSIGNED_VALUES) {
			query.append(JOIN_PRODUCT_ATTRIBUTE_VALUE)
			.append(JOIN_PRODUCT_AND_CATEGORY_PLACEHOLDER)
			.append(SQLQueryUtils.buildPlaceholderComparisonClause(lowerHierarchy.size()));
		}
		

		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			query.append(GROUP_BY_VALUE);
		}
		query.append(ORDER_BY_CLAUSE);

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					query.toString(), 
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY
					);
			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			for (Short id : upperHierarchy.keySet()) {
				stmt.setShort(i++, id);
			}
			if (!returnUnassigned) {
				for (Short id : lowerHierarchy.keySet()) {
					stmt.setShort(i++, id);
				}
			}

			rs = stmt.executeQuery();
			return loadResults(rs);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Map<String, AttributeDTO<?>> findByProduct(Connection conn, Long productId, Locale locale) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(
					FINDBYPRODUCT_QUERY, 
					ResultSet.TYPE_FORWARD_ONLY, 
					ResultSet.CONCUR_READ_ONLY
					);

			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setLong(i++, productId);

			rs = stmt.executeQuery();
			return loadResults(rs);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	/**
	 * Load all the attributes returned from an SQL SELECT query, creating objects of the
	 * correct type for each one, inserting them into a single map using a wildcard.
	 * 
	 * @param rs ResultSet returned from the query
	 * @return Attribute map containing all the data from the ResultSet
	 * @throws SQLException propagated from the driver
	 * @throws DataException if an unexpected data type identifier was retrieved from the ResultSet.
	 */
	private Map<String, AttributeDTO<?>> loadResults(ResultSet rs) 
			throws SQLException, DataException {

		Map<String, AttributeDTO<?>> results = new TreeMap<String, AttributeDTO<?>>();

		while (rs.next()) {
			addValue(rs, results);
		}

		return results;
	}

	/**
	 * Creates an object containing an attribute value and either adds it to its corresponding attribute type
	 * that was previously loaded into the results map, or creates a new type and retrieves its information
	 * in order to add it into said map.
	 * 
	 * @param <T> Type of object to create in order to store the retrieved value correctly.
	 * @param rs ResultSet returned from the query
	 * @param currentResults Map containing the results loaded from the result set up until the method call
	 * @param retrievedValue Value to add to the map
	 * @throws SQLException propagated by the driver
	 */
	private void addValue(ResultSet rs, Map<String, AttributeDTO<?>> currentResults) 
			throws SQLException, DataException {

		AttributeDTO<?> next = loadNext(rs);
		String name = next.getName();

		if (currentResults.containsKey(name)) { // Add value to previously retrieved attribute type
			AttributeDTO<?> attribute = currentResults.get(name);

			for (AttributeValueDTO<?> attributeValue : next.getValues()) {
				attribute.addValue(attributeValue.getId(), attributeValue.getValue());
			}
		} else { 
			currentResults.put(name, next);
		}
	}

	private AttributeDTO<?> loadNext(ResultSet rs) throws SQLException {

		AttributeDTO<?> attribute = AttributeDTO.getInstance(rs.getString(DATA_TYPE_COLUMN));
		attribute.setId(rs.getInt(ID_COLUMN));
		attribute.setName(rs.getString(NAME_COLUMN));

		// Add value to list
		Class<?> parameterizedTypeClass = (Class<?>) attribute.getTypeParameterClass();
		Long id = rs.getLong(VALUE_ID_ALIAS);

		if (!rs.wasNull()) { // Attribute without values
			Object value = rs.getObject(AttributeUtils.getValueColumnName(attribute), parameterizedTypeClass);
			attribute.addValue(id, value);
		}

		return attribute;
	}

	@Override
	public Boolean assignToProduct(Connection conn, AbstractProductDTO p) throws DataException {

		if (p == null || p.getAttributes() == null || p.getAttributes().isEmpty()) {
			return false;
		}

		PreparedStatement stmt = null;
		int attributeCount = getAttributeCount(p);
		String query = new StringBuilder(ASSIGN_QUERY)
				.append(SQLQueryUtils.buildPlaceholderValuesClause(attributeCount, ASSIGN_QUERY_COLUMN_COUNT))
				.toString();

		try {

			unassign(conn, p.getId());
			stmt = conn.prepareStatement(query);

			int stmtIndex = 1;
			for (AttributeDTO<?> attribute : p.getAttributes().values()) {
				for (int valueIndex = 0; valueIndex < attribute.getValues().size(); valueIndex++) {
					AttributeValueDTO<?> av = attribute.getValues().get(valueIndex);
					if (av.getId() == null) { // Attribute value was not previously retrieved from database
						identifyOrCreate(conn, av, attribute.getId(), attribute.getDataTypeIdentifier());
					}
					stmt.setLong(stmtIndex++, p.getId());
					stmt.setLong(stmtIndex++, av.getId());
				}
			}

			int affectedRows = stmt.executeUpdate();
			return affectedRows == attributeCount;

		} catch (SQLException sqle) {

			logger.error(sqle);
			throw new DataException(sqle);

		} finally {
			JDBCUtils.close(stmt);
		}
	}

	/**
	 * Counts the number of attribute values in a product in order to format the SQL query that assigns them.
	 * 
	 * @param p Product containing the attribute values to count
	 * @return number of attribute values
	 */
	private static int getAttributeCount(AbstractProductDTO p) {
		int count = 0;
		for (AttributeDTO<?> attribute : p.getAttributes().values()) {
			count+=attribute.getValues().size();
		}
		return count;
	}

	/**
	 * Executes an SQL DELETE query erasing all relations between a product and its attributes, in order to avoid
	 * constraint violations while updating said product.
	 * 
	 * @param conn Connection to use to execute the query
	 * @param productId Identifier for the product
	 * @throws DataException if an SQLException is caught
	 */
	private void unassign(Connection conn, Long productId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UNASSIGN_QUERY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, productId);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}	
	}

	/**
	 * Attempts to find the identifier for an attribute value where it is missing. If that fails, 
	 * inserts the attribute value into the database.
	 * 
	 * @param conn Connection to use to execute the querias
	 * @param av Attribute value containing the object to insert
	 * @param dataType Data type of the object to insert
	 * @param attributeId Name of the attribute type of the object to insert
	 * @return Database ID for the attribute value
	 * @throws DataException if driver throws SQLException
	 */
	private Long identifyOrCreate(Connection conn, AttributeValueDTO<?> attributeValue, Integer attributeId, String dataTypeIdentifier) 
			throws DataException {

		String columnName = AttributeUtils.getValueColumnName(dataTypeIdentifier);
		Integer targetSqlType = AttributeUtils.getTargetSqlTypeIdentifier(dataTypeIdentifier);
		Object value = attributeValue.getValue();

		// Double-check that attribute value isn't in the database already
		Long id = findValueId(conn, value, attributeId, columnName, targetSqlType);

		if (id == null) { // Insert into database

			PreparedStatement stmt = null;
			ResultSet rs = null;

			try {
				stmt = conn.prepareStatement(
						String.format(CREATE_IF_ABSENT_QUERY, columnName), 
						Statement.RETURN_GENERATED_KEYS
						);
				int i = 1;
				stmt.setInt(i++, attributeId);
				stmt.setObject(i++, value, targetSqlType);


				int affectedRows = stmt.executeUpdate();
				if (affectedRows != 1) { 
					throw new DataException(ErrorCodes.INSERT_FAILED);
				}
				rs = stmt.getGeneratedKeys();
				if (rs.first()) {
					id = rs.getLong(JDBCUtils.GENERATED_KEY_INDEX);
				}
			} catch (SQLException e) {
				logger.error(e);
				throw new DataException(e);
			} finally {
				JDBCUtils.close(stmt, rs);
			}
		}

		attributeValue.setId(id);
		return id;
	} 

	private Long findValueId(Connection conn, Object value, Integer attributeId, String columnName, int targetSqlType) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Long id = null;

		try {
			stmt = conn.prepareStatement(
					String.format(FINDIDBYVALUE_QUERY, columnName),
					ResultSet.TYPE_FORWARD_ONLY,
					ResultSet.CONCUR_READ_ONLY
					);
			int i = 1;
			stmt.setObject(i++, value, targetSqlType);
			stmt.setInt(i++, attributeId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				id = rs.getLong(ID_COLUMN);
			}
			return id;

		} catch (SQLException e) {
			logger.error(e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

}
