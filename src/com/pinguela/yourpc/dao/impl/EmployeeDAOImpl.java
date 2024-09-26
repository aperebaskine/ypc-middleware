package com.pinguela.yourpc.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.model.EmployeeDepartment;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class EmployeeDAOImpl
extends AbstractDAO<Integer, Employee>
implements EmployeeDAO {
		
	private static final String UPDATE_PASSWORD_QUERY =
			" UPDATE EMPLOYEE"
			+ " SET PASSWORD = ?"
			+ "	WHERE ID = ?";

	private static Logger logger = LogManager.getLogger(EmployeeDAOImpl.class);
	
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
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Employee> query, Root<Employee> root,
			AbstractCriteria<Employee> criteria) {
		
		EmployeeCriteria employeeCriteria = (EmployeeCriteria) criteria;
		
		if (employeeCriteria.getFirstName() != null) {
			query.where(builder.like(root.get("name").get("firstName"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getFirstName())));
		}
		if (employeeCriteria.getLastName1() != null) {
			query.where(builder.like(root.get("name").get("lastName1"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getLastName1())));
		}
		if (employeeCriteria.getLastName2() != null) {
			query.where(builder.like(root.get("name").get("lastName2"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getLastName2())));
		}
		if (employeeCriteria.getDocumentNumber() != null) {
			query.where(builder.equal(root.get("document").get("number"), employeeCriteria.getDocumentNumber()));
		}
		if (employeeCriteria.getPhoneNumber() != null) {
			query.where(builder.equal(root.get("phone"), employeeCriteria.getPhoneNumber()));
		}
		if (employeeCriteria.getEmail() != null) {
			query.where(builder.equal(root.get("email"), employeeCriteria.getEmail()));
		}
		if (employeeCriteria.getUsername() != null) {
			query.where(builder.equal(root.get("username"), employeeCriteria.getUsername()));
		}
		if (employeeCriteria.getDepartmentId() != null) {
			Join<Employee, EmployeeDepartment> joinDepartment = root.join("departmentHistory");
			joinDepartment.on(builder.equal(joinDepartment.get("departmentId"), employeeCriteria.getDepartmentId()));
			joinDepartment.on(builder.isNull(joinDepartment.get("endDate")));
		}
	}

	@Override
	public Integer create(Session session, Employee e) 
			throws DataException {
		return super.persist(session, e);
	}

	@Override
	public Boolean update(Session session, Employee e) 
			throws DataException {
		return super.merge(session, e);
	} 
	
	@Override
	public Boolean updatePassword(Session session, Integer employeeId, String password) throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_PASSWORD_QUERY);
			int i = 1;
			stmt.setString(i++, password);
			stmt.setInt(i++, employeeId);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			} else {
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public Boolean delete(Session session, Integer employeeId) 
			throws DataException {
		return super.remove(session, employeeId);
	}

}
