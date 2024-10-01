package com.pinguela.yourpc.dao.impl;

import static com.pinguela.yourpc.dao.impl.AttributeDAOImpl.ATTRIBUTE_ALIAS;
import static com.pinguela.yourpc.dao.impl.AttributeDAOImpl.ATTRIBUTE_COLUMN_NAMES;
import static com.pinguela.yourpc.dao.impl.AttributeDAOImpl.ATTRIBUTE_VALUE_ALIAS;
import static com.pinguela.yourpc.dao.impl.AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_NAMES;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.util.HibernateUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.Tuple;

public class TemporaryTestDAO {

	private static final String PRODUCT_ALIAS = "p";

	private static final String SELECT_QUERY_PLACEHOLDER =
			" SELECT %1$s"
					+ " FROM PRODUCT %2$s"
					+ " LEFT JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.PRODUCT_ID = %2$s.ID"
					+ " LEFT JOIN ATTRIBUTE_VALUE %3$s"
					+ " ON %3$s.ID = pav.ATTRIBUTE_VALUE_ID"
					+ "	LEFT JOIN ATTRIBUTE_TYPE %4$s"
					+ " on %4$s.ID = %3$s.ATTRIBUTE_TYPE_ID";

	private static final String[] PRODUCT_COLUMN_NAMES = {
			"ID",
			"NAME",
			"CATEGORY_ID",
			"DESCRIPTION",
			"LAUNCH_DATE",
			"DISCONTINUATION_DATE",
			"STOCK",
			"PURCHASE_PRICE",
			"SALE_PRICE",
			"REPLACEMENT_ID"
	};

	private static final String getColumns() {
		String[] productColumns = 
				SQLQueryUtils.applyAlias(
						PRODUCT_ALIAS, PRODUCT_COLUMN_NAMES);

		String[] attributeColumns = 
				SQLQueryUtils.applyAlias(
						ATTRIBUTE_ALIAS, ATTRIBUTE_COLUMN_NAMES);

		String[] attributeValueColumns = 
				SQLQueryUtils.applyAlias(
						ATTRIBUTE_VALUE_ALIAS, ATTRIBUTE_VALUE_COLUMN_NAMES);

		return SQLQueryUtils.getColumnClause(productColumns, attributeColumns, attributeValueColumns);
	}

	private static final String BASE_QUERY = 
			String.format(
					SELECT_QUERY_PLACEHOLDER, 
					getColumns(), 
					PRODUCT_ALIAS, 
					AttributeDAOImpl.ATTRIBUTE_VALUE_ALIAS,
					AttributeDAOImpl.ATTRIBUTE_ALIAS
					);


	public static void main(String[] args) {

		Map<Long, Product> productMap = new HashMap<>();
		Map<String, Attribute<?>> attributeMap = new HashMap<>();

		Session session = HibernateUtils.openSession();

		String queryStr = BASE_QUERY
				+ " WHERE p.NAME LIKE :name";

		NativeQuery<Object[]> query = session.createNativeQuery(queryStr, Object[].class);
		
		for (Entry<String, Class<?>> entry : Attribute.TYPE_PARAMETER_CLASSES.entrySet()) {
			query.addScalar(AttributeUtils.getValueColumnName(entry.getKey()), entry.getValue());
		}

		List<Object[]> list = query
				.setParameter("name", "%Ryzen%")
				.getResultList();
		
		System.out.println(list);

		for (Object[] tuple : list) {
			for (Object o : tuple) {
				System.out.println(o);
			}
		}
	}

}
