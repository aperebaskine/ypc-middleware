package com.pinguela.yourpc.model;

import java.util.Map.Entry;

import com.pinguela.yourpc.dao.util.AttributeUtils;

public class AttributeStatisticsCriteria 
extends AbstractStatisticsCriteria<Attribute<?>> {
	
	// TODO: Move logic to DAO
	public AttributeStatisticsCriteria() {
		for (Entry<String, Boolean> clause : AttributeUtils.ATTRIBUTE_ORDER_BY_CLAUSES.entrySet()) {
			orderBy(clause.getKey(), clause.getValue());
		}
	}
	
}
