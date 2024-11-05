package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Category_;
import com.pinguela.yourpc.model.EntityCriteria;
import com.pinguela.yourpc.model.dto.CategoryDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CategoryDAOImpl
extends AbstractDAO<Short, Category>
implements CategoryDAO {

	public CategoryDAOImpl() {
	}

	@Override
	public Map<Short, CategoryDTO> findAll(Session session, Locale locale) throws DataException {
		
		List<CategoryDTO> categories = super.findBy(session, CategoryDTO.class, new EntityCriteria<Short, Category>(locale));
		return mapByPrimaryKey(categories);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Category> root,
			AbstractCriteria<Category> criteria) {
		root.fetch(Category_.parent, JoinType.LEFT);
		root.fetch(Category_.children, JoinType.LEFT);
		return null;
	}

}
