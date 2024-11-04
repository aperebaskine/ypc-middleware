package com.pinguela.yourpc.service;

import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.ProductDTO;

public interface ProductService {
	
	public Long create(ProductDTO p)
			throws ServiceException, DataException;
	
	public Boolean update(ProductDTO p)
			throws ServiceException, DataException;
	
	public Boolean delete(Long productId)
			throws ServiceException, DataException;
	
	public ProductDTO findById(Long id, Locale locale)
			throws ServiceException, DataException;
	
	public Results<ProductDTO> findBy(ProductCriteria criteria, int startPos, int pageSize)
			throws ServiceException, DataException;
	
	public ProductRanges getRanges(ProductCriteria criteria) 
			throws ServiceException, DataException;

}
