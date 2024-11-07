package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.dto.CategoryDTO;

public class CategoryDAOImpl
extends AbstractDAO<Short, Category>
implements CategoryDAO {

	public CategoryDAOImpl() {
	}

	@Override
	public Map<Short, CategoryDTO> findAll(Session session, Locale locale) throws DataException {
		
		List<CategoryDTO> categories = getSelectionQueryBuilder(session, CategoryDTO.class)
				.buildSelectionQuery(session, null, locale)
				.getResultList();
		return mapByPrimaryKey(categories);
	}

}
