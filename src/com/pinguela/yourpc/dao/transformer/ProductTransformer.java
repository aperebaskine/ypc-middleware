package com.pinguela.yourpc.dao.transformer;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Product;

public class ProductTransformer 
implements TupleTransformer<Product> {
	
	private Session session;
	
	private AttributeTransformer attributeTransformer;
	
	private Map<Long, Product> productMap;
	private Map<String, Integer> aliasMap;
	
	public ProductTransformer(Session session) {
		this.session = session;
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
			int index = 1;
			Product q = new Product();
			q.setId(id);
			q.setName((String) tuple[index++]);
			q.setCategory(session.getReference(Category.class, tuple[index++]));
			q.setDescription((String) tuple[index++]);
			q.setLaunchDate((Date) tuple[index++]);
			q.setDiscontinuationDate((Date) tuple[index++]);
			q.setStock((Integer) tuple[index++]);
			
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
