package com.pinguela.yourpc.dao.transformer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Product;

public class ProductTransformer 
implements TupleTransformer<Product>, ResultListTransformer<Product> {

	private Session session;
	private AttributeTransformer attributeTransformer;

	private Map<Long, Product> productMap;
	
	private Map<Long, Short> categoryIdMap;
	private Map<Long, Long> replacementIdMap;

	public ProductTransformer(Session session) {
		this.session = session;
		attributeTransformer = new AttributeTransformer();
		productMap = new HashMap<>();
		categoryIdMap = new HashMap<>();
		replacementIdMap = new HashMap<>();
	}

	@Override
	public Product transformTuple(Object[] tuple, String[] aliases) {

		Long id = (Long) tuple[0];

		Product product = productMap.computeIfAbsent(id, idKey -> {
			int index = 1;
			Product newProduct = new Product();
			newProduct.setId(id);
			newProduct.setName((String) tuple[index++]);
			
			categoryIdMap.put(id, (Short) tuple[index++]);

			newProduct.setDescription((String) tuple[index++]);
			newProduct.setLaunchDate((Date) tuple[index++]);
			newProduct.setDiscontinuationDate((Date) tuple[index++]);
			newProduct.setStock((Integer) tuple[index++]);
			newProduct.setPurchasePrice((Double) tuple[index++]);
			newProduct.setSalePrice((Double) tuple[index++]);
			
			Long replacementId = (Long) tuple[index++];

			if (replacementId != null) {
				replacementIdMap.put(id, (Long) tuple[index++]);
			}

			return newProduct;
		});

		// TODO Auto-generated method stub
		return product;
	}
	
	@Override
	public List<Product> transformList(List<Product> resultList) {
		List<Product> uniqueItems = new ArrayList<Product>();
		
		Iterator<Product> i = resultList.iterator();
		
		while (i.hasNext()) {
			Product p = i.next();
			if (!uniqueItems.contains(p)) {
				uniqueItems.add(p);
			}
		}
		
		for (Product p : uniqueItems) {
			p.setCategory(session.getReference(Category.class, categoryIdMap.get(p.getId())));
			
			Long replacementId = replacementIdMap.get(p.getId());
			if (replacementId != null) {
				p.setReplacement(session.getReference(Product.class, replacementId));
			}
		}
		
		return uniqueItems;
	}

}
