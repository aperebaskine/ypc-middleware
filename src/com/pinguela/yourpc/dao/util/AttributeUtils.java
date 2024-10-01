package com.pinguela.yourpc.dao.util;

import static com.pinguela.yourpc.model.constants.AttributeValueHandlingModes.RANGE;
import static com.pinguela.yourpc.model.constants.AttributeValueHandlingModes.SET;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class AttributeUtils {

	private static final String COLUMN_NAME_PLACEHOLDER = "VALUE_%s";

	/**
	 * Mapping of the attribute data type identifier constants to the
	 * {@link java.sql.Types} SQL data type identifier constants.
	 */
	private static final Map<String, Integer> SQL_TARGET_TYPE_IDENTIFIERS;

	/**
	 * Mapping of the attribute data type identifier constants to their
	 * corresponding SQL data type names.
	 */
	private static final Map<String, String> SQL_DATA_TYPE_NAMES;
	
	/**
	 * Mapping of the attribute data type identifier constants to their
	 * corresponding database columns.
	 */
	private static final Map<String, String> COLUMN_NAMES;

	static {
		Map<String, String> dataTypeConstantMap = getDataTypeConstants();

		SQL_TARGET_TYPE_IDENTIFIERS = initializeSqlTypeIdentifierMap(dataTypeConstantMap);
		SQL_DATA_TYPE_NAMES = initializeSqlTypeNameMap(dataTypeConstantMap);
		COLUMN_NAMES = initializeColumnNameMap(dataTypeConstantMap);
	}

	private static final Map<String, String> getDataTypeConstants() {
		Field[] dataTypeConstants = AttributeDataTypes.class.getFields();
		Map<String, String> dataTypeNameMap = new HashMap<String, String>();
		for (Field dataTypeConstant : dataTypeConstants) {
			try {
				dataTypeNameMap.put(dataTypeConstant.getName(), (String) dataTypeConstant.get(null));
			} catch (IllegalAccessException e) {
				// Retrieving constants from an interface, no exception should be thrown;
			}
		}
		return dataTypeNameMap;
	}

	private static final Map<String, Integer> initializeSqlTypeIdentifierMap(Map<String, String> dataTypeConstants) {

		Map<String, Integer> sqlTypeIdentifierMap = new HashMap<String, Integer>();
		for (String dataTypeName : dataTypeConstants.keySet()) {
			try {
				sqlTypeIdentifierMap.put(dataTypeConstants.get(dataTypeName), 
						(Integer) Types.class.getField(dataTypeName).get(null));
			} catch (Exception e) {

			}
		}
		return Collections.unmodifiableMap(sqlTypeIdentifierMap);
	}

	private static final Map<String, String> initializeSqlTypeNameMap(Map<String, String> dataTypeConstants) {

		Map<String, String> sqlTypeNameMap = new HashMap<String, String>();
		for (String dataTypeName : dataTypeConstants.keySet()) {
			sqlTypeNameMap.put(dataTypeConstants.get(dataTypeName), dataTypeName);
		}
		return Collections.unmodifiableMap(sqlTypeNameMap);
	}
	
	private static final Map<String, String> initializeColumnNameMap(Map<String, String> dataTypeConstants) {
		Map<String, String> columnNameMap = new LinkedHashMap<String, String>();
		for (String dataTypeName : dataTypeConstants.keySet()) {
			columnNameMap.put(dataTypeConstants.get(dataTypeName), 
					String.format(COLUMN_NAME_PLACEHOLDER, dataTypeName));
		}
		return Collections.unmodifiableMap(columnNameMap);
	}
	
	public static final int getTargetSqlTypeIdentifier(String dataTypeIdentifier) {
		return SQL_TARGET_TYPE_IDENTIFIERS.get(dataTypeIdentifier);
	}

	public static final int getTargetSqlTypeIdentifier(Attribute<?> attribute) {
		return getTargetSqlTypeIdentifier(attribute.getDataTypeIdentifier());
	}

	public static final String getTargetSqlTypeName(String dataTypeIdentifier) {
		return SQL_DATA_TYPE_NAMES.get(dataTypeIdentifier);
	}

	public static final String getTargetSqlTypeName(Attribute<?> attribute) {
		return getTargetSqlTypeName(attribute.getDataTypeIdentifier());
	}

	public static final String getValueColumnName(String dataTypeIdentifier) {
		return COLUMN_NAMES.get(dataTypeIdentifier);
	}

	public static final String getValueColumnName(Attribute<?> attribute) {
		return getValueColumnName(attribute.getDataTypeIdentifier());
	}

	public static String buildAttributeConditionClause(Map<String, Attribute<?>> attributes) {

		List<StringBuilder> conditions = new ArrayList<StringBuilder>(attributes.size());

		for (Attribute<?> attribute : attributes.values()) {
			StringBuilder condition = new StringBuilder(" (at.NAME = ? AND");

			if (attribute.getValues().size() == 1) {
				condition.append(String.format(" av.%1$s = ?)", getValueColumnName(attribute)));
			} else {
				switch (attribute.getValueHandlingMode()) {
				case RANGE:
					condition.append(String.format(" av.%1$s >= ? AND av.%1$s <= ?)",
							getValueColumnName(attribute)));
					break;
				case SET:
					condition.append(String.format(" av.%1$s", getValueColumnName(attribute)))
					.append(SQLQueryUtils.buildPlaceholderComparisonClause(attribute.getValues().size()))
					.append(")");
					break;
				}
			}
			conditions.add(condition);
		}
		return new StringBuilder(" (").append(String.join(" OR", conditions)).append(")").toString();
	}

}
