package com.pinguela.yourpc.dao.impl.builder;

import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.dto.AbstractProductDTO;

public abstract class AbstractProductQueryBuilder<D extends AbstractProductDTO> 
extends AbstractNativeQueryBuilder<Long, Product, D, ProductCriteria> {

	protected AbstractProductQueryBuilder(Class<D> dtoClass) {
		super(dtoClass, Product.class);
	}

}
