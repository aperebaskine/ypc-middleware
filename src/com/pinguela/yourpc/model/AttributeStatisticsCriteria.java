package com.pinguela.yourpc.model;

public class AttributeStatisticsCriteria 
extends AbstractStatisticsCriteria<Attribute<?>> {
	
	// TODO: Generate automatically
	private static final String[] ORDER_BY_NAME_AND_VALUES = {"name", "value_bigint", "value_varchar", "value_decimal", "value_boolean"};

	@Override
	protected void setDefaultOrder() {
		for (String column : ORDER_BY_NAME_AND_VALUES) {
			orderBy(column, ASC);
		}
	}

}
