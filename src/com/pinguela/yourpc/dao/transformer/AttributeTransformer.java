package com.pinguela.yourpc.dao.transformer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.Attribute;

public class AttributeTransformer 
implements TupleTransformer<Attribute<?>> {
	
	private Map<String, Attribute<?>> attributeMap;
	
	private Map<String, Integer> aliasMap;
	
	public AttributeTransformer() {
		attributeMap = new LinkedHashMap<String, Attribute<?>>();
		aliasMap = new LinkedHashMap<String, Integer>();
	}

	@Override
	public Attribute<?> transformTuple(Object[] tuple, String[] aliases) {
		return transformTuple(0, tuple, aliases);
	}
	
	public Attribute<?> transformTuple(int offset, Object[] tuple, String[] aliases) {
		
		int index = offset;
		Integer id = (Integer) tuple[index++];
		String dataTypeId = (String) tuple[index++];
		String attributeName = (String) tuple[index++];
		
		Attribute<?> attribute = attributeMap.computeIfAbsent(attributeName, name -> {
			Attribute<?> newAttribute = Attribute.getInstance(dataTypeId);
			newAttribute.setId(id);
			newAttribute.setName(name);
			
			aliasMap.computeIfAbsent(dataTypeId, idKey -> {
				return mapValueColumn(dataTypeId, offset, aliases);
			});
			
			return newAttribute;
		});
		
		attribute.addValue((Long) tuple[index++], tuple[aliasMap.get(attribute.getDataTypeIdentifier())]);
		
		return attribute;
	}
	
	private Integer mapValueColumn(String dataTypeId, int offset, String[] aliases) {
		String columnName = AttributeUtils.getValueColumnName(dataTypeId);
		
		for (int i = offset; i < aliases.length; i++) {
			if (aliases[i].contains(columnName)) {
				return i;
			}
		}
		
		throw new IllegalArgumentException("Unrecognized attribute data type ID.");
	}
	
}
