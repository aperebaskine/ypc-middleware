package com.pinguela.yourpc.dao.impl;

import static com.pinguela.yourpc.model.constants.AttributeValueHandlingModes.RANGE;
import static com.pinguela.yourpc.model.constants.AttributeValueHandlingModes.SET;

import java.lang.reflect.Field;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinguela.yourpc.model.constants.AttributeDataTypes;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.util.SQLQueryUtils;

class AttributeUtils {

	static final String PRE_FORMAT_COLUMN_NAME = "VALUE_%s";

	/**
	 * Mapping of the attribute data type identifier constants to the
	 * {@link java.sql.Types} SQL data type identifier constants.
	 */
	static final Map<String, Integer> SQL_TARGET_TYPE_IDENTIFIERS;

	/**
	 * Mapping of the attribute data type identifier constants to their
	 * corresponding SQL data type names.
	 */
	static final Map<String, String> SQL_DATA_TYPE_NAMES;

	static {
		Map<String, String> dataTypeConstantMap = getDataTypeConstants();

		SQL_TARGET_TYPE_IDENTIFIERS = initializeSqlTypeIdentifierMap(dataTypeConstantMap);
		SQL_DATA_TYPE_NAMES = initializeSqlTypeNameMap(dataTypeConstantMap);
	}

	static final Map<String, String> getDataTypeConstants() {
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

	static final Map<String, Integer> initializeSqlTypeIdentifierMap(Map<String, String> dataTypeConstants) {

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

	static final Map<String, String> initializeSqlTypeNameMap(Map<String, String> dataTypeConstants) {

		Map<String, String> sqlTypeNameMap = new HashMap<String, String>();
		for (String dataTypeName : dataTypeConstants.keySet()) {
			sqlTypeNameMap.put(dataTypeConstants.get(dataTypeName), dataTypeName);
		}
		return Collections.unmodifiableMap(sqlTypeNameMap);
	}
	
	static final int getTargetSqlTypeIdentifier(String dataTypeIdentifier) {
		return SQL_TARGET_TYPE_IDENTIFIERS.get(dataTypeIdentifier);
	}

	static final int getTargetSqlTypeIdentifier(AttributeDTO<?> attribute) {
		return getTargetSqlTypeIdentifier(attribute.getDataTypeIdentifier());
	}

	static final String getTargetSqlTypeName(String dataTypeIdentifier) {
		return SQL_DATA_TYPE_NAMES.get(dataTypeIdentifier);
	}

	static final String getTargetSqlTypeName(AttributeDTO<?> attribute) {
		return getTargetSqlTypeName(attribute.getDataTypeIdentifier());
	}

	static final String getValueColumnName(String dataTypeIdentifier) {
		return String.format(PRE_FORMAT_COLUMN_NAME, getTargetSqlTypeName(dataTypeIdentifier));
	}

	static final String getValueColumnName(AttributeDTO<?> attribute) {
		return getValueColumnName(attribute.getDataTypeIdentifier());
	}

	static String buildAttributeConditionClause(List<AttributeDTO<?>> attributes) {

		List<StringBuilder> conditions = new ArrayList<StringBuilder>(attributes.size());

		for (AttributeDTO<?> attribute : attributes) {
			StringBuilder condition = new StringBuilder(" (at.ID = ? AND");

			if (attribute.getValuesByHandlingMode().size() == 1) {
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
