package com.pinguela.yourpc.dao;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;

public interface ProductDAO {
	
	public Long create(Session session, LocalizedProductDTO p)
			throws DataException;
	
	public Boolean update(Session session, LocalizedProductDTO p)
			throws DataException;
	
	public Boolean delete(Session session, Long productId)
			throws DataException;
	
	public LocalizedProductDTO findById(Session session, Long id)
			throws DataException;
	
	public Results<LocalizedProductDTO> findBy(Session session, ProductCriteria criteria, int pos, int pageSize)
			throws DataException;
	
	public ProductRanges getRanges(Session session, ProductCriteria criteria) 
			throws DataException;
	
}
