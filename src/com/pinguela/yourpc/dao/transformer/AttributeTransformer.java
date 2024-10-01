package com.pinguela.yourpc.dao.transformer;

import java.util.Map;

import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.model.Attribute;

public class AttributeTransformer 
implements TupleTransformer<Attribute<?>> {
	
	private Map<String, Attribute<?>> attributeMap;
	private Map<String, Integer> aliasMap;

	@Override
	public Attribute<?> transformTuple(Object[] tuple, String[] aliases) {
		// TODO Auto-generated method stub
		return null;
	}

}
