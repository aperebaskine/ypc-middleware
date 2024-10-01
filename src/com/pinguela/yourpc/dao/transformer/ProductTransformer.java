package com.pinguela.yourpc.dao.transformer;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Product;

class ProductTransformer 
implements TupleTransformer<Product> {
	
	private AttributeTransformer attributeTransformer;
	
	private Map<Long, Product> productMap;
	private Map<String, Integer> aliasMap;
	
	public ProductTransformer() {
		attributeTransformer = new AttributeTransformer();
		productMap = new HashMap<>();
		aliasMap = new HashMap<String, Integer>();
	}
	
	public void clear() {
		productMap.clear();
		aliasMap.clear();
	}

	@Override
	public Product transformTuple(Object[] tuple, String[] aliases) {
		if (aliasMap.isEmpty()) {
			mapAliases(aliases);
		}
		
		Long id = (Long) tuple[0];
		Product p = productMap.computeIfAbsent(id, idKey -> {
			Product q = new Product();
			q.setId(id);
			return q;
		});
		
		if (aliasMap.containsKey("ATTRIBUTE_DATA_TYPE_ID")) {
			Attribute<?> attribute = attributeTransformer.transformTuple(tuple, aliases);
			p.addAttribute(attribute);
		}
		
		// TODO Auto-generated method stub
		return p;
	}
	
	private void mapAliases(String[] aliases) {
		for (int i = 0; i < aliases.length; i++) {
			aliasMap.put(aliases[i], i);
		}
	}

}
