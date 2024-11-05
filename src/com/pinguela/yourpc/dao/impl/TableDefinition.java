package com.pinguela.yourpc.dao.impl;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.dto.AttributeDTOFactory;
import com.pinguela.yourpc.util.SQLQueryUtils;

public final class TableDefinition {
	
	private TableDefinition() {
	}
	
	public static final String PRODUCT_ALIAS = "p";
	public static final String PRODUCT_SUBQUERY_ALIAS = "q";
	public static final String REPLACEMENT_ALIAS = "r";
	
	public static final String ATTRIBUTE_ALIAS = "a";
	public static final String ATTRIBUTE_VALUE_ALIAS = "av";
	
	public static final Map<String, Class<?>> PRODUCT_COLUMNS = defineProductColumns();
	public static final Map<String, Class<?>> ATTRIBUTE_COLUMNS = defineAttributeColumns();
	public static final Map<String, Class<?>> ATTRIBUTE_VALUE_COLUMNS = defineAttributeValueColumns();

	private static final Map<String, Class<?>> defineProductColumns() {
	    Map<String, Class<?>> columns = new LinkedHashMap<>();
	    
	    addColumn(columns, PRODUCT_ALIAS, "ID", Long.class);
	    addColumn(columns, PRODUCT_ALIAS, "NAME", String.class);
	    addColumn(columns, PRODUCT_ALIAS, "CATEGORY_ID", Short.class);
	    addColumn(columns, PRODUCT_ALIAS, "DESCRIPTION", String.class);
	    addColumn(columns, PRODUCT_ALIAS, "LAUNCH_DATE", Date.class);
	    addColumn(columns, PRODUCT_ALIAS, "DISCONTINUATION_DATE", Date.class);
	    addColumn(columns, PRODUCT_ALIAS, "STOCK", Integer.class);
	    addColumn(columns, PRODUCT_ALIAS, "PURCHASE_PRICE", Double.class);
	    addColumn(columns, PRODUCT_ALIAS, "SALE_PRICE", Double.class);
	    addColumn(columns, PRODUCT_ALIAS, "REPLACEMENT_ID", Long.class);
	    addColumn(columns, REPLACEMENT_ALIAS, "NAME", String.class);

	    return Collections.unmodifiableMap(columns);
	}

	private static final Map<String, Class<?>> defineAttributeColumns() {
	    Map<String, Class<?>> columns = new LinkedHashMap<>();
	    
	    addColumn(columns, ATTRIBUTE_ALIAS, "ID", Integer.class);
	    addColumn(columns, ATTRIBUTE_ALIAS, "ATTRIBUTE_DATA_TYPE_ID", String.class);
	    addColumn(columns, ATTRIBUTE_ALIAS, "NAME", String.class);

	    return Collections.unmodifiableMap(columns);
	}

	private static final Map<String, Class<?>> defineAttributeValueColumns() {
	    Map<String, Class<?>> columns = new LinkedHashMap<>();
	    
	    addColumn(columns, ATTRIBUTE_VALUE_ALIAS, "ID", Long.class);

	    for (Entry<String, Class<?>> entry : AttributeDTOFactory.TYPE_PARAMETER_CLASSES.entrySet()) {
	        addColumn(columns, ATTRIBUTE_VALUE_ALIAS, 
	            AttributeUtils.getValueColumnName(entry.getKey()), entry.getValue());
	    }

	    return Collections.unmodifiableMap(columns);
	}

	private static final void addColumn(Map<String, Class<?>> map, String alias, String columnName, Class<?> type) {
		map.put(SQLQueryUtils.applyTableAlias(alias, columnName), type);
	}

}
