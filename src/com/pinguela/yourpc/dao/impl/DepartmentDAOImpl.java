package com.pinguela.yourpc.dao.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.DepartmentDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Department;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class DepartmentDAOImpl 
extends AbstractDAO<String, Department>
implements DepartmentDAO {
	
	public DepartmentDAOImpl() {
	}

	@Override
	public Map<String, Department> findAll(Session session) throws DataException {
		List<Department> departments = super.findBy(session, null);
		Map<String, Department> departmentsById = new LinkedHashMap<String, Department>();
		
		for (Department d : departments) {
			departmentsById.put(d.getId(), d);
		}
		
		return departmentsById;
	}

	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Department> query, Root<Department> root,
			AbstractCriteria<Department> criteria) {}

}
