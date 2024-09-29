package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Department_;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.model.EmployeeDepartment;
import com.pinguela.yourpc.model.EmployeeDepartment_;
import com.pinguela.yourpc.model.Employee_;
import com.pinguela.yourpc.model.FullName_;
import com.pinguela.yourpc.model.ID_;
import com.pinguela.yourpc.model.SimpleUpdateValue;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeDAOImpl
extends AbstractMutableDAO<Integer, Employee>
implements EmployeeDAO {
	
	public EmployeeDAOImpl() {
	}

	@Override
	public Employee findById(Session session, Integer employeeId) 
			throws DataException {
		return super.findById(session, employeeId);
	}

	@Override
	public Employee findByUsername(Session session, String username) 
			throws DataException {	
		EmployeeCriteria criteria = new EmployeeCriteria();
		criteria.setUsername(username);
		return super.findSingleResultBy(session, criteria);
	}

	@Override
	public List<Employee> findBy(Session session, EmployeeCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Employee> root, AbstractCriteria<Employee> criteria) {
	    EmployeeCriteria employeeCriteria = (EmployeeCriteria) criteria;
	    List<Predicate> predicates = new ArrayList<>();

	    if (employeeCriteria.getFirstName() != null) {
	        predicates.add(builder.like(root.get(Employee_.name).get(FullName_.firstName), 
	                SQLQueryUtils.wrapLike(employeeCriteria.getFirstName())));
	    }
	    if (employeeCriteria.getLastName1() != null) {
	        predicates.add(builder.like(root.get(Employee_.name).get(FullName_.lastName1), 
	                SQLQueryUtils.wrapLike(employeeCriteria.getLastName1())));
	    }
	    if (employeeCriteria.getLastName2() != null) {
	        predicates.add(builder.like(root.get(Employee_.name).get(FullName_.lastName2), 
	                SQLQueryUtils.wrapLike(employeeCriteria.getLastName2())));
	    }
	    if (employeeCriteria.getDocumentNumber() != null) {
	        predicates.add(builder.equal(root.get(Employee_.document).get(ID_.number), employeeCriteria.getDocumentNumber()));
	    }
	    if (employeeCriteria.getPhoneNumber() != null) {
	        predicates.add(builder.equal(root.get(Employee_.phoneNumber), employeeCriteria.getPhoneNumber()));
	    }
	    if (employeeCriteria.getEmail() != null) {
	        predicates.add(builder.equal(root.get(Employee_.email), employeeCriteria.getEmail()));
	    }
	    if (employeeCriteria.getUsername() != null) {
	        predicates.add(builder.equal(root.get(Employee_.username), employeeCriteria.getUsername()));
	    }
	    if (employeeCriteria.getDepartmentId() != null) {
	        Join<Employee, EmployeeDepartment> joinDepartment = root.join(Employee_.departmentHistory);
	        joinDepartment.on(builder.equal(joinDepartment.get(EmployeeDepartment_.department).get(Department_.id), employeeCriteria.getDepartmentId()));
	        joinDepartment.on(builder.isNull(joinDepartment.get(EmployeeDepartment_.endDate)));
	    }

	    return predicates;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Employee> query, Root<Employee> root,
			AbstractCriteria<Employee> criteria) {
		// Unused	
	}

	@Override
	public Integer create(Session session, Employee e) 
			throws DataException {
		return super.createEntity(session, e);
	}

	@Override
	public Boolean update(Session session, Employee e) 
			throws DataException {
		return super.updateEntity(session, e);
	} 
	
	@Override
	public Boolean updatePassword(Session session, Integer employeeId, String password) throws DataException {
		EmployeeCriteria criteria = new EmployeeCriteria();
		criteria.setId(employeeId);
		SimpleUpdateValue<Employee> values = new SimpleUpdateValue<>(password);
		
		return super.updateBy(session, criteria, values);
	}
	
	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Employee> updateQuery, Root<Employee> root,
			AbstractUpdateValues<Employee> updateValues) {
		updateQuery.set(root.get(Employee_.encryptedPassword), (String) ((SimpleUpdateValue<Employee>) updateValues).getValue());
	}

	@Override
	public Boolean delete(Session session, Integer employeeId) 
			throws DataException {
		return super.deleteEntity(session, employeeId);
	}

}
