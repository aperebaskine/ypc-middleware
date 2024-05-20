package com.pinguela.yourpc.dao;

import java.sql.Connection;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;

public interface ProductDAO {
	
	public Long create(Connection conn, Product p)
			throws DataException;
	
	public Boolean update(Connection conn, Product p)
			throws DataException;
	
	public Boolean delete(Connection conn, Long productId)
			throws DataException;
	
	public Product findById(Connection conn, Long id)
			throws DataException;
	
	public Results<Product> findBy(Connection conn, ProductCriteria criteria, int pos, int pageSize)
			throws DataException;
	
	public ProductRanges getRanges(Connection conn, ProductCriteria criteria) 
			throws DataException;
	
}
