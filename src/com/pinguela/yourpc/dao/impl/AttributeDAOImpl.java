package com.pinguela.yourpc.dao.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.transformer.AttributeTransformer;
import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.AttributeValue;
import com.pinguela.yourpc.service.AttributeService;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class AttributeDAOImpl 
extends AbstractMutableDAO<Integer, Attribute<?>>
implements AttributeDAO {

	private static Logger logger = LogManager.getLogger(AttributeDAOImpl.class);

	static final Map<String, String> ATTRIBUTE_COLUMN_ALIASES = 
			SQLQueryUtils.generateColumnAliases(Attribute.class, TableDefinition.ATTRIBUTE_COLUMNS.keySet());
	static final Map<String, String> ATTRIBUTE_VALUE_COLUMN_ALIASES = 
			SQLQueryUtils.generateColumnAliases(AttributeValue.class, TableDefinition.ATTRIBUTE_VALUE_COLUMNS.keySet());

	private static final String SELECT_QUERY_PLACEHOLDER = 
			" SELECT %1$s"
					+ " FROM ATTRIBUTE_TYPE a"
					+ " LEFT JOIN ATTRIBUTE_VALUE av"
					+ " on a.ID = av.ATTRIBUTE_TYPE_ID";

	private static final String CATEGORY_JOIN = 
			" INNER JOIN CATEGORY_ATTRIBUTE_TYPE cat"
					+ " ON a.ID = cat.ATTRIBUTE_TYPE_ID";

	private static final String PRODUCT_JOIN = 
			" INNER JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON a.ID = pav.ATTRIBUTE_VALUE_ID";

	private static final String GROUP_BY_VALUE = 
			" GROUP BY a.ID, av.ID";

	private static final String CREATE_IF_ABSENT_QUERY =
			" INSERT INTO ATTRIBUTE_VALUE(ATTRIBUTE_TYPE_ID, %1$s)"
					+ " SELECT ID AS ATTRIBUTE_TYPE_ID, :value AS %1$s" 
					+ " FROM ATTRIBUTE_TYPE WHERE NAME = :attributeName";

	private static final String FIND_ID_QUERY = 
			String.format(SELECT_QUERY_PLACEHOLDER, " av.ID")
			+ " WHERE a.NAME = :attributeName AND av.%1$s = :value";

	private static final String getColumns() {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES);
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES);

		return SQLQueryUtils.createColumnClause(columns);
	}

	private static final String BASE_QUERY = 
			String.format(SELECT_QUERY_PLACEHOLDER, getColumns());

	public AttributeDAOImpl() {
	}

	@Override
	public Attribute<?> findByName(Session session, String name, boolean returnUnassigned) throws DataException {
		StringBuilder queryStr = new StringBuilder(BASE_QUERY);
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			queryStr.append(PRODUCT_JOIN);
		}
		queryStr.append(" WHERE a.NAME = ?");
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			queryStr.append(GROUP_BY_VALUE);
		}

		return executeFindByQuery(session, queryStr, Arrays.asList(name)).get(name);
	}

	@Override
	public Map<String, Attribute<?>> findByCategory(Session session, Short categoryId, boolean returnUnassigned)
			throws DataException {

		StringBuilder queryStr = new StringBuilder(BASE_QUERY).append(CATEGORY_JOIN);
		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			queryStr.append(PRODUCT_JOIN);
		}

		Set<Short> categoryHierarchy = CategoryUtils.getLowerHierarchy(categoryId).keySet();
		queryStr.append(" WHERE cat.CATEGORY_ID")
		.append(SQLQueryUtils.buildPlaceholderComparisonClause(categoryHierarchy));

		if (returnUnassigned != AttributeService.RETURN_UNASSIGNED_VALUES) {
			queryStr.append(GROUP_BY_VALUE);
		}

		return executeFindByQuery(session, queryStr, categoryHierarchy);
	}

	@Override
	public Map<String, Attribute<?>> findByProduct(Session session, Long productId) throws DataException {

		StringBuilder queryStr = new StringBuilder(BASE_QUERY)
				.append(PRODUCT_JOIN)
				.append(" WHERE pav.PRODUCT_ID = ?")
				.append(GROUP_BY_VALUE);

		return executeFindByQuery(session, queryStr, Arrays.asList(productId));
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	private Map<String, Attribute<?>> executeFindByQuery(Session session, StringBuilder builder, Collection<?> keys) 
			throws DataException {
		builder.append(SQLQueryUtils.buildOrderByClause(AttributeUtils.ATTRIBUTE_ORDER_BY_CLAUSES, getTargetClass()));

		try {
			NativeQuery query = session.createNativeQuery(builder.toString(), Object[].class);
			AttributeTransformer transformer = new AttributeTransformer();
			query.setTupleTransformer(transformer);
			query.setResultListTransformer(transformer);

			int i = 1;
			for (Object key : keys) {
				query.setParameter(i++, key);
			}

			return ((Map<String, Attribute<?>>) query.getResultList().get(0));

		} catch (HibernateException he) {
			logger.error(he);
			throw new DataException(he);
		}
	}

	@Override
	public Boolean saveAttributeValues(Session session, Map<String, Attribute<?>> attributes) throws DataException {

		if (attributes == null || attributes.isEmpty()) {
			return false;
		}

		try {			
			for (Attribute<?> attribute : attributes.values()) {
				for (int i = 0; i < attribute.getValues().size(); i++) {
					AttributeValue<?> av = attribute.getValues().get(i);
					if (av.getId() == null) { // Attribute value was not previously retrieved from database
						identifyOrCreate(session, av, attribute.getName(), attribute.getDataTypeIdentifier());
					}
				}
			}
			return validateInsert(attributes);
		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			throw new DataException(he);
		} 
	}
	
	private boolean validateInsert(Map<String, Attribute<?>> attributes) {
		for (Attribute<?> attribute : attributes.values()) {
			for (AttributeValue<?> value : attribute.getValues()) {
				if (value.getId() == null) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Attempts to find the identifier for an attribute value where it is missing. If that fails, 
	 * inserts the attribute value into the database.
	 * 
	 * @param conn Connection to use to execute the querias
	 * @param av Attribute value containing the object to insert
	 * @param dataType Data type of the object to insert
	 * @param attributeName Name of the attribute type of the object to insert
	 * @return Database ID for the attribute value
	 * @throws DataException if driver throws SQLException
	 */
	private Long identifyOrCreate(Session session, AttributeValue<?> attributeValue, String attributeName, String dataTypeIdentifier) 
			throws DataException {

		String columnName = AttributeUtils.getValueColumnName(dataTypeIdentifier);
		Object value = attributeValue.getValue();

		// Double-check that attribute value isn't in the database already
		Long id = findValueId(session, value, attributeName, columnName);

		if (id == null) { // Insert into database
			try {
				NativeQuery<?> query = session.createNativeQuery(String.format(CREATE_IF_ABSENT_QUERY, columnName), getTargetClass());

				query.setParameter("value", value);
				query.setParameter("attributeName", attributeName);

				int affectedRows = query.executeUpdate();
				if (affectedRows != 1) { 
					throw new DataException(ErrorCodes.INSERT_FAILED);
				}
				
				id = session.createNativeQuery("SELECT LAST_INSERT_ID()", Long.class).getSingleResult();

			} catch (HibernateException e) {
				logger.error(e.getMessage(), e);
				throw new DataException(e);
			}
		}

		attributeValue.setId(id);
		return id;
	} 

	private Long findValueId(Session session, Object value, String attributeName, String columnName) 
			throws DataException {
		try {
			return session.createNativeQuery(String.format(FIND_ID_QUERY, columnName), Long.class)
					.setParameter("attributeName", attributeName)
					.setParameter("value", value)
					.getSingleResultOrNull();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} 
	}

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Attribute<?>> updateQuery,
			Root<Attribute<?>> root, AbstractUpdateValues<Attribute<?>> updateValues) {
		// Unused
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Attribute<?>> root,
			AbstractCriteria<Attribute<?>> criteria) {
		// Unused
		return null;
	}

	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Attribute<?>> query, Root<Attribute<?>> root,
			AbstractCriteria<Attribute<?>> criteria) {
		// Unused

	}

}
