package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.DocumentTypeDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.IDType;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DocumentTypeDAOImpl 
extends AbstractDAO<String, IDType>
implements DocumentTypeDAO {
	
	public DocumentTypeDAOImpl() {
	}
	
	@Override
	public Map<String, IDType> findAll(Session session) throws DataException {
		List<IDType> documentTypes = super.findBy(session, null);
		return mapByPrimaryKey(documentTypes);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<IDType> root,
			AbstractCriteria<IDType> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<IDType> query, Root<IDType> root,
			AbstractCriteria<IDType> criteria) {
		// Unused	
	}
}
