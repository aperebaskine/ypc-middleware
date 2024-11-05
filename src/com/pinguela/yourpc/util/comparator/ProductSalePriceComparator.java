package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.dto.AbstractProductDTO;

public class ProductSalePriceComparator 
implements Comparator<AbstractProductDTO> {
	
	@Override
	public int compare(AbstractProductDTO o1, AbstractProductDTO o2) {
		return o1.getSalePrice().compareTo(o2.getSalePrice());
	}

}
