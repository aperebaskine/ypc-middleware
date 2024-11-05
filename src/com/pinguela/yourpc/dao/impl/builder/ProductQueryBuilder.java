package com.pinguela.yourpc.dao.impl.builder;

import org.hibernate.query.Query;

import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class ProductQueryBuilder extends AbstractCriteriaQueryBuilder<Long, Product, ProductDTO, ProductCriteria> {

	@Override
	protected void select(CriteriaQuery<ProductDTO> query, CriteriaBuilder builder, Root<Product> root,
			ProductCriteria criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void join(CriteriaQuery<ProductDTO> query, CriteriaBuilder builder, Root<Product> root,
			ProductCriteria criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void where(CriteriaQuery<ProductDTO> query, CriteriaBuilder builder, Root<Product> root,
			ProductCriteria criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void groupBy(CriteriaQuery<ProductDTO> query, CriteriaBuilder builder, Root<Product> root,
			ProductCriteria criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParameters(Query<ProductDTO> query, ProductCriteria criteria) {
		// TODO Auto-generated method stub
		
	}

	

}
