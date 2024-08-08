package com.pinguela.yourpc.model;

public class AttributeStatisticsCriteria 
extends AbstractStatisticsCriteria<Attribute<?>> {
	
	// TODO: String[]
	private static final String ORDER_BY_NAME_AND_VALUES = "at.NAME ASC, av.VALUE_BIGINT ASC, av.VALUE_VARCHAR ASC, av.VALUE_DECIMAL ASC, av.VALUE_BOOLEAN ASC";

	@Override
	protected void setDefaultOrdering() {
		setOrderBy(ORDER_BY_NAME_AND_VALUES);
		setAscDesc(ASC);
	}

}
