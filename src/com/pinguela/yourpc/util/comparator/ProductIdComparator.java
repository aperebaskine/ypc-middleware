package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.Product;

public class ProductIdComparator 
implements Comparator<Product> {
	
	@Override
	public int compare(Product aProduct, Product anotherProduct) {
		return aProduct.getId().compareTo(anotherProduct.getId());
	}

}
