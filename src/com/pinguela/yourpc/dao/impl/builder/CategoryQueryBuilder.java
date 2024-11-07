package com.pinguela.yourpc.dao.impl.builder;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.hibernate.query.Query;

import com.pinguela.yourpc.annotation.QueryBuilder;
import com.pinguela.yourpc.dao.impl.QueryType;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.CategoryLocale_;
import com.pinguela.yourpc.model.Category_;
import com.pinguela.yourpc.model.EntityCriteria;
import com.pinguela.yourpc.model.dto.CategoryDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@QueryBuilder(entity = Category.class, dto = CategoryDTO.class, type = QueryType.SELECT)
public class CategoryQueryBuilder 
extends AbstractCriteriaQueryBuilder<Short, Category, CategoryDTO, EntityCriteria<Short, Category>> {
	
	public CategoryQueryBuilder() {
		super(CategoryDTO.class, Category.class);
	}

	@Override
	protected void select(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		query.select(builder.construct(getDtoClass(), 
				root.get(Category_.id), 
				root.join(Category_.categoryLocale)
				.get(CategoryLocale_.name), 
				root.get(Category_.parent), 
				root.get(Category_.children)));
	}

	@Override
	protected void join(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		root.fetch(Category_.parent, JoinType.LEFT);
		root.fetch(Category_.children, JoinType.LEFT);
	}

	@Override
	protected List<Predicate> where(CriteriaBuilder builder, Root<Category> root, Locale locale,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		return Collections.emptyList();
	}

	@Override
	protected void groupBy(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void having(CriteriaQuery<CategoryDTO> query, CriteriaBuilder builder, Root<Category> root,
			EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void setParameters(Query<?> query, EntityCriteria<Short, Category> criteria) {
		// TODO Auto-generated method stub
		
	}

}
