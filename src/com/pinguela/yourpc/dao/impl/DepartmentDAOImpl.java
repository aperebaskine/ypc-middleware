package com.pinguela.yourpc.dao.impl;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.DepartmentDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Department;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class DepartmentDAOImpl 
extends AbstractDAO<String, Department>
implements DepartmentDAO {
	
	public DepartmentDAOImpl() {
	}

	@Override
	public Map<String, Department> findAll(Session session) throws DataException {
		List<Department> departments = super.findBy(session, null);
		return mapByPrimaryKey(departments);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Department> root,
			AbstractCriteria<Department> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Department> query, Root<Department> root,
			AbstractCriteria<Department> criteria) {
		// Unused	
	}

}
