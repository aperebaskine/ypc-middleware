package com.pinguela.yourpc.dao.impl.builder;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.pinguela.yourpc.annotation.QueryBuilder;
import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.EntityCriteria;
import com.pinguela.yourpc.model.dto.CategoryDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@QueryBuilder(dto = CategoryDTO.class, type = QueryType.SELECT)
public class CategoryQueryBuilder 
extends AbstractCriteriaQueryBuilder<Short, Category, CategoryDTO, EntityCriteria<Short, Category>> {
	
	public CategoryQueryBuilder() {
		super(CategoryDTO.class, Category.class);
	}
	
	@Override
	protected void select(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void join(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void where(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void groupBy(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Query<CategoryDTO> buildQuery(Session session, Short id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Query<CategoryDTO> buildQuery(Session session, EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		return null;
	}

}
