package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.Map;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.Product;

public interface AttributeDAO {
	
	public Attribute<?> findById(Connection conn, Long id, boolean returnUnassigned)
			throws DataException;
	
	/**
	 * Returns all the attributes that can be associated to a given category, including the set of
	 * all possible values for said attribute. Calling class may determine whether to return values
	 * that are not currently assigned to a non-discontinued product.
	 * 
	 * @param conn Connection to the database that is being used to execute the queries
	 * @param categoryId Primary key identifier of the category
	 * @param returnUnassigned Indicates whether to return values that aren't assigned to a
	 * non-discontinued product
	 * @return Map containing the set of attributes, mapped to their name, and containing all their 
	 * possible values for a given category
	 * @throws DataException if driver throws SQLException
	 */
	public Map<String, Attribute<?>> findByCategory(Connection conn, Short categoryId, boolean returnUnassigned)
			throws DataException;
	
	/**
	 * Returns all the attributes that have been assigned to a given product.
	 * 
	 * @param conn Connection to the database that is being used to execute the query
	 * @param productId Primary key identifier of the product
	 * @return Map containing a product's set of attributes, mapped to their name.
	 * @throws DataException if driver throws SQLException
	 */
	public Map<String, Attribute<?>> findByProduct(Connection conn, Long productId)
			throws DataException;

	/**
	 * Assigns attribute values to a product, inserting to the database any value that's absent from it.
	 * 
	 * @param conn Connection to the database that is being used to execute the query
	 * @param p Product containing attributes to assign
	 * @return true if the assign statement was successful, else false
	 * @throws DataException
	 */
	public Boolean assignToProduct(Connection conn, Product p)
			throws DataException;
	
}
