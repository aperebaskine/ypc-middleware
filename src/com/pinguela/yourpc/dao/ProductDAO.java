package com.pinguela.yourpc.dao;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductDTO;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;

public interface ProductDAO {
	
	public Long create(Session session, ProductDTO p)
			throws DataException;
	
	public Boolean update(Session session, ProductDTO p)
			throws DataException;
	
	public Boolean delete(Session session, Long productId)
			throws DataException;
	
	public ProductDTO findById(Session session, Long id)
			throws DataException;
	
	public Results<ProductDTO> findBy(Session session, ProductCriteria criteria, int pos, int pageSize)
			throws DataException;
	
	public ProductRanges getRanges(Session session, ProductCriteria criteria) 
			throws DataException;
	
}
