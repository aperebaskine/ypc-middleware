package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Category_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CategoryDAOImpl
extends AbstractDAO<Short, Category>
implements CategoryDAO {

	public CategoryDAOImpl() {
	}

	@Override
	public Map<Short, Category> findAll(Session session) throws DataException {
		List<Category> categories = super.findBy(session, null);
		return mapByPrimaryKey(categories);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Category> root,
			AbstractCriteria<Category> criteria) {
		root.fetch(Category_.parent, JoinType.LEFT);
		root.fetch(Category_.children, JoinType.LEFT);
		return null;
	}	
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Category> query, Root<Category> root,
			AbstractCriteria<Category> criteria) {
		// Unused
	}

}
