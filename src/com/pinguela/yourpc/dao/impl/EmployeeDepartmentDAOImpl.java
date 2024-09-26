package com.pinguela.yourpc.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Department;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeDepartment;
import com.pinguela.yourpc.model.SimpleCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class EmployeeDepartmentDAOImpl
extends AbstractMutableDAO<Integer, EmployeeDepartment>
implements EmployeeDepartmentDAO {

	public EmployeeDepartmentDAOImpl() {
	}

	@Override
	public List<EmployeeDepartment> findByEmployee(Session session, Integer employeeId) throws DataException {
		SimpleCriteria<EmployeeDepartment> criteria = new SimpleCriteria<EmployeeDepartment>(employeeId);
		return super.findBy(session, criteria);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<EmployeeDepartment> root,
			AbstractCriteria<EmployeeDepartment> criteria) {
		return Arrays.asList(builder.equal(root.get("employee").get("id"), 
				((SimpleCriteria<EmployeeDepartment>) criteria).getValue()));
	}

	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<EmployeeDepartment> query,
			Root<EmployeeDepartment> root, AbstractCriteria<EmployeeDepartment> criteria) {
		// Unused
	}

	@Override
	public Integer assignToEmployee(Session session, Integer employeeId, String departmentId) throws DataException {
		EmployeeDepartment employeeDepartment = new EmployeeDepartment();
		employeeDepartment.setEmployee(session.find(Employee.class, employeeId));
		employeeDepartment.setDepartment(session.find(Department.class, departmentId));
		return super.createEntity(session, employeeDepartment);
	}

	@Override
	public Boolean unassign(Session session, Integer employeeId) throws DataException {
		return super.deleteBy(session, new SimpleCriteria<EmployeeDepartment>(employeeId));
	}

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<EmployeeDepartment> updateQuery,
			Root<EmployeeDepartment> root, AbstractUpdateValues<EmployeeDepartment> updateValues) {
		// Unused
	}

}
