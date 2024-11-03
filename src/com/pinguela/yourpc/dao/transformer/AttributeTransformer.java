package com.pinguela.yourpc.dao.transformer;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.Attribute;

public class AttributeTransformer 
implements TupleTransformer<Attribute<?>>, ResultListTransformer<Map<String, Attribute<?>>> {

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

		if (dataTypeId == null) {
			return null;
		}

		Attribute<?> attribute = attributeMap.computeIfAbsent(attributeName, name -> {
			Attribute<?> newAttribute = Attribute.getInstance(dataTypeId);
			newAttribute.setId(id);
			newAttribute.setName(name);

			aliasMap.computeIfAbsent(dataTypeId, idKey -> {
				return mapValueColumn(dataTypeId, offset, aliases);
			});

			return newAttribute;
		});

		Long valueId = (Long) tuple[index++];

		if (valueId != null) {
			attribute.addValue(valueId, tuple[aliasMap.get(attribute.getDataTypeIdentifier())]);
		}

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

	@Override
	public List<Map<String, Attribute<?>>> transformList(List<Map<String, Attribute<?>>> resultList) {
		return Arrays.asList(attributeMap);
	}

}
