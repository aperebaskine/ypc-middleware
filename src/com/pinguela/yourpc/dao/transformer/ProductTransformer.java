package com.pinguela.yourpc.dao.transformer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;

import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.util.HibernateUtils;

public class ProductTransformer 
implements TupleTransformer<Product>, ResultListTransformer<Object> {

	private Session session;
	private AttributeTransformer attributeTransformer;

	private Map<Long, Product> productMap;
	private Map<String, Integer> aliasMap;

	public ProductTransformer() {
		this.session = HibernateUtils.openSession();
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

		Long id = (Long) tuple[0];

		Product product = productMap.computeIfAbsent(id, idKey -> {
			int index = 1;
			Product newProduct = new Product();
			newProduct.setId(id);
			newProduct.setName((String) tuple[index++]);

			newProduct.setCategory(session.getReference(Category.class, tuple[index++]));

			newProduct.setDescription((String) tuple[index++]);
			newProduct.setLaunchDate((Date) tuple[index++]);
			newProduct.setDiscontinuationDate((Date) tuple[index++]);
			newProduct.setStock((Integer) tuple[index++]);
			newProduct.setPurchasePrice((Double) tuple[index++]);
			newProduct.setSalePrice((Double) tuple[index++]);

			Long replacementId = (Long) tuple[index++];

			if (replacementId != null) {
				newProduct.setReplacement(session.getReference(Product.class, replacementId));
			}

			return newProduct;
		});

		// TODO Auto-generated method stub
		return product;
	}
	
	@Override
	public List<Object> transformList(List<Object> resultList) {
		session.close();
		return resultList;
	}

}
