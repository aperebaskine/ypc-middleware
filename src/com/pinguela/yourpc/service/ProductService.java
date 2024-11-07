package com.pinguela.yourpc.service;

import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.ProductDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;

public interface ProductService {
	
	public Long create(ProductDTO dto)
			throws ServiceException, DataException;
	
	public Boolean update(ProductDTO dto)
			throws ServiceException, DataException;
	
	public Boolean delete(Long productId)
			throws ServiceException, DataException;
	
	public ProductDTO findById(Long id)
			throws ServiceException, DataException;
	
	public LocalizedProductDTO findById(Long id, Locale locale)
			throws ServiceException, DataException;
	
	public Results<LocalizedProductDTO> findBy(ProductCriteria criteria, int startPos, int pageSize)
			throws ServiceException, DataException;
	
	public ProductRanges getRanges(ProductCriteria criteria) 
			throws ServiceException, DataException;

}
