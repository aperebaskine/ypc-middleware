package com.pinguela.yourpc.model.constants;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines the constants corresponding to the SQL database's primary key
 * that are used to identify the attribute's data type.
 * 
 * <p><b>Constant names must be identical to the corresponding constant in
 * {@link java.sql.Types}.</b></p>
 */
public final class AttributeDataTypes {
	
	public static final String VARCHAR = "VAR";
	public static final String BIGINT = "INT";
	public static final String DECIMAL = "DEC";
	public static final String BOOLEAN = "BOO";
	
	private static Set<String> types = initSet();
	
	private AttributeDataTypes() {
	}
	
	private static Set<String> initSet() {
		Set<String> set = new HashSet<String>();
		try {
			for (Field field : AttributeDataTypes.class.getFields()) {
				set.add((String) field.get(null));
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return set;
	}
	
	public static boolean isValidType(String type) {
		return types.contains(type);
	}

}
