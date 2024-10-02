package com.pinguela.yourpc.dao.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.pinguela.yourpc.dao.transformer.ProductTransformer;
import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.util.HibernateUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class TemporaryTestDAO {
	
	static {
		try {
			Class.forName("com.pinguela.yourpc.util.HibernateUtils");
			Class.forName("com.pinguela.yourpc.dao.impl.AttributeDAOImpl");
		} catch (ClassNotFoundException e) {
			System.exit(0);
		}
	}

	private static final String PRODUCT_ALIAS = "p";
	
	private static final Map<String, Class<?>> PRODUCT_COLUMNS = defineProductColumns();
	
	private static final Map<String, String> PRODUCT_COLUMN_ALIASES = SQLQueryUtils.generateColumnAliases(Product.class, PRODUCT_COLUMNS.keySet());

	private static final String SELECT_QUERY_PLACEHOLDER =
			" SELECT %1$s"
					+ " FROM PRODUCT %2$s"
					+ " LEFT JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.PRODUCT_ID = %2$s.ID"
					+ " LEFT JOIN ATTRIBUTE_VALUE %3$s"
					+ " ON %3$s.ID = pav.ATTRIBUTE_VALUE_ID"
					+ "	LEFT JOIN ATTRIBUTE_TYPE %4$s"
					+ " on %4$s.ID = %3$s.ATTRIBUTE_TYPE_ID";
	
	private static final Map<String, Class<?>> defineProductColumns() {
	    Map<String, Class<?>> columns = new LinkedHashMap<>();
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "ID"), java.lang.Long.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "NAME"), java.lang.String.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "CATEGORY_ID"), java.lang.Short.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "DESCRIPTION"), java.lang.String.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "LAUNCH_DATE"), java.util.Date.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "DISCONTINUATION_DATE"), java.util.Date.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "STOCK"), java.lang.Integer.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "PURCHASE_PRICE"), java.lang.Double.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "SALE_PRICE"), java.lang.Double.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "REPLACEMENT_ID"), java.lang.Long.class);
	    return Collections.unmodifiableMap(columns);
	}

	private static final String getColumns() {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		columns.putAll(PRODUCT_COLUMN_ALIASES);
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES);
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES);

		return SQLQueryUtils.createColumnClause(columns);
	}

	private static final String BASE_QUERY = 
			String.format(
					SELECT_QUERY_PLACEHOLDER, 
					getColumns(), 
					PRODUCT_ALIAS, 
					AttributeDAOImpl.ATTRIBUTE_VALUE_ALIAS,
					AttributeDAOImpl.ATTRIBUTE_ALIAS
					);
	
	private void testNativeQuery() {
		Map<Long, Product> productMap = new HashMap<>();
		Map<String, Attribute<?>> attributeMap = new HashMap<>();

		Session session = HibernateUtils.openSession();

		String queryStr = BASE_QUERY
				+ " WHERE p.ID = :id";

		NativeQuery<Object> query = session.createNativeQuery(queryStr, Object.class);
		query.setTupleTransformer(new ProductTransformer());
				
		for (Entry<String, Class<?>> entry : Attribute.TYPE_PARAMETER_CLASSES.entrySet()) {
			query.addScalar(AttributeUtils.getValueColumnName(entry.getKey()), entry.getValue());
		}

		List<Object> list = query
				.setParameter("id", 1l)
				.getResultList();

		for (Object tuple : list) {
			System.out.println(((Product) tuple).getCategoryId());
		}
	}


	public static void main(String[] args) {

		Map<Long, Product> productMap = new HashMap<>();
		Map<String, Attribute<?>> attributeMap = new HashMap<>();

		Session session = HibernateUtils.openSession();

		String queryStr = BASE_QUERY
				+ " WHERE p.ID = :id";

		NativeQuery<Object> query = session.createNativeQuery(queryStr, Object.class);
		
		ProductTransformer transformer = new ProductTransformer();
		query.setTupleTransformer(transformer);
		query.setResultListTransformer(transformer);
		
		for (String column : PRODUCT_COLUMNS.keySet()) {
			query.addScalar(PRODUCT_COLUMN_ALIASES.get(column), PRODUCT_COLUMNS.get(column));
		}
		
		for (String column : AttributeDAOImpl.ATTRIBUTE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES.get(column), AttributeDAOImpl.ATTRIBUTE_COLUMNS.get(column));
		}
		
		for (String column : AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES.get(column), AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMNS.get(column));
		}
		
		List<Object> list = query
				.setParameter("id", 1l)
				.getResultList();
		
		for (Object object : list) {
			System.out.println(((Product) object).getId());
		}
		
	}

}
