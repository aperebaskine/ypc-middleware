package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.Product;

public class ProductSalePriceComparator 
implements Comparator<Product> {
	
	@Override
	public int compare(Product o1, Product o2) {
		return o1.getSalePrice().compareTo(o2.getSalePrice());
	}

}
