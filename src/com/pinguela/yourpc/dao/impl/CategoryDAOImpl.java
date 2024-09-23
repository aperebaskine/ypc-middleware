package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Category;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public class CategoryDAOImpl
extends AbstractDAO<Category>
implements CategoryDAO {
	
	public CategoryDAOImpl() {
		super(Category.class);
	}
	
	@Override
	public Map<Short, Category> findAll(Session session) throws DataException {
		List<Category> categories = super.findBy(session, null);
		Map<Short, Category> categoriesById = new LinkedHashMap<>();
		
		for (Category c : categories) {
			categoriesById.put(c.getId(), c);
		}
		
		return categoriesById;
	}

	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Category> query, Root<Category> root,
			AbstractCriteria<Category> criteria) {
		root.fetch("parent", JoinType.LEFT);
		root.fetch("children", JoinType.LEFT);
	}	

}
