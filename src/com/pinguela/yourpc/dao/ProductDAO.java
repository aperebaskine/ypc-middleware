package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.FullProductDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;

public interface ProductDAO {
	
	public Long create(Connection conn, FullProductDTO p)
			throws DataException;
	
	public Boolean update(Connection conn, FullProductDTO p)
			throws DataException;
	
	public Boolean delete(Connection conn, Long productId)
			throws DataException;
	
	public FullProductDTO findById(Connection conn, Long id)
			throws DataException;
	
	public LocalizedProductDTO findById(Connection conn, Long id, Locale locale)
			throws DataException;
	
	public Results<LocalizedProductDTO> findBy(Connection conn, ProductCriteria criteria, int pos, int pageSize)
			throws DataException;
	
	public ProductRanges getRanges(Connection conn, ProductCriteria criteria) 
			throws DataException;
	
}
