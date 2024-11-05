package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.dto.AbstractProductDTO;

public class ProductIdComparator 
implements Comparator<AbstractProductDTO> {
	
	@Override
	public int compare(AbstractProductDTO aProduct, AbstractProductDTO anotherProduct) {
		return aProduct.getId().compareTo(anotherProduct.getId());
	}

}
