package com.pinguela.yourpc.service;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;

public interface ProductService {
	
	public Long create(Product p)
			throws ServiceException, DataException;
	
	public Boolean update(Product p)
			throws ServiceException, DataException;
	
	public Boolean delete(Long productId)
			throws ServiceException, DataException;
	
	public Product findById(Long id)
			throws ServiceException, DataException;
	
	public Results<Product> findBy(ProductCriteria criteria, int startPos, int pageSize)
			throws ServiceException, DataException;
	
	public ProductRanges getRanges(ProductCriteria criteria) 
			throws ServiceException, DataException;

}
