package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
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
extends AbstractDAO<Employee>
implements EmployeeDAO {
	
	private static final String SELECT_COLUMNS =
			" SELECT e.ID, e.FIRST_NAME, e.LAST_NAME1, e.LAST_NAME2, e.DOCUMENT_TYPE_ID, dt.NAME, e.DOCUMENT_NUMBER, e.PHONE, e.EMAIL,"
					+ " e.USERNAME, e.PASSWORD, e.CREATION_DATE, e.IBAN, e.BIC, e.SUPERVISOR_ID, f.FIRST_NAME, f.LAST_NAME1, f.LAST_NAME2";

	private static final String FROM_TABLE =
			" FROM EMPLOYEE e"
					+ " INNER JOIN DOCUMENT_TYPE dt"
					+ "	ON e.DOCUMENT_TYPE_ID = dt.ID"
					+ " LEFT JOIN EMPLOYEE f"
					+ " ON e.SUPERVISOR_ID = f.ID";
	private static final String JOIN_EMPLOYEE_DEPARTMENT =
			" INNER JOIN EMPLOYEE_DEPARTMENT ed"
			+ " ON ed.EMPLOYEE_ID = e.ID"
			+ " AND ed.END_DATE IS NULL";

	private static final String ID_FILTER =
			" WHERE e.ID = ? AND e.TERMINATION_DATE IS NULL";

	private static final String USERNAME_FILTER = 
			" WHERE e.USERNAME = ? AND e.TERMINATION_DATE IS NULL";

	private static final String FINDBY_QUERY = SELECT_COLUMNS +FROM_TABLE;
	private static final String FINDBYID_QUERY = FINDBY_QUERY +ID_FILTER;
	private static final String FINDBYEMAIL_QUERY = FINDBY_QUERY +USERNAME_FILTER;
	
	private static final String CREATE_QUERY =
			"INSERT INTO EMPLOYEE(SUPERVISOR_ID, FIRST_NAME, LAST_NAME1, LAST_NAME2, DOCUMENT_TYPE_ID, DOCUMENT_NUMBER, PHONE, EMAIL, USERNAME, PASSWORD, CREATION_DATE, IBAN, BIC)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY =
			" UPDATE EMPLOYEE"
					+ " SET SUPERVISOR_ID = ?,"
					+ " FIRST_NAME = ?,"
					+ " LAST_NAME1 = ?,"
					+ " LAST_NAME2 = ?,"
					+ " DOCUMENT_TYPE_ID = ?,"
					+ " DOCUMENT_NUMBER = ?,"
					+ " PHONE = ?,"
					+ " EMAIL = ?,"
					+ " USERNAME = ?,"
					+ " PASSWORD = ?,"
					+ " CREATION_DATE = ?,"
					+ " IBAN = ?,"
					+ " BIC = ?"
					+ " WHERE ID = ?";
	
	private static final String UPDATE_PASSWORD_QUERY =
			" UPDATE EMPLOYEE"
			+ " SET PASSWORD = ?"
			+ "	WHERE ID = ?";
	
	private static final String DELETE_QUERY =
			" UPDATE EMPLOYEE SET TERMINATION_DATE = ? WHERE ID = ?";

	private static Logger logger = LogManager.getLogger(EmployeeDAOImpl.class);
	private AddressDAO addressDAO = null;
	private EmployeeDepartmentDAO employeeDepartmentDAO = null;
	
	public EmployeeDAOImpl() {
		super(Employee.class);
		addressDAO = new AddressDAOImpl();
		employeeDepartmentDAO = new EmployeeDepartmentDAOImpl();
	}

	@Override
	public Employee findById(Session session, Integer employeeId) 
			throws DataException {
		return super.findById(session, employeeId);
	}

	@Override
	public Employee findByUsername(Session session, String username) 
			throws DataException {	
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Employee> query = builder.createQuery(getTargetClass());
			Root<Employee> root = query.from(getTargetClass());

			query.where(builder.equal(root.get("username"), username));
			return session.createQuery(query).getSingleResult();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
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
			query.where(builder.like(root.join("firstName"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getFirstName())));
		}
		if (employeeCriteria.getLastName1() != null) {
			query.where(builder.like(root.join("lastName1"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getLastName1())));
		}
		if (employeeCriteria.getLastName2() != null) {
			query.where(builder.like(root.join("lastName2"), 
					SQLQueryUtils.wrapLike(employeeCriteria.getLastName2())));
		}
		if (employeeCriteria.getDocumentNumber() != null) {
			query.where(builder.equal(root.join("number"), employeeCriteria.getDocumentNumber()));
		}
		if (employeeCriteria.getPhoneNumber() != null) {
			query.where(builder.equal(root.join("phone"), employeeCriteria.getPhoneNumber()));
		}
		if (employeeCriteria.getEmail() != null) {
			query.where(builder.equal(root.join("email"), employeeCriteria.getEmail()));
		}
		if (employeeCriteria.getUsername() != null) {
			query.where(builder.equal(root.join("username"), employeeCriteria.getUsername()));
		}
		if (employeeCriteria.getDepartmentId() != null) {
			Join<Employee, EmployeeDepartment> joinDepartment = root.join("departmentHistory");
			query.where(builder.equal(joinDepartment.get("id"), employeeCriteria.getDepartmentId()));
			query.where(builder.isNull(joinDepartment.get("endDate")));
		}
	}

	@Override
	public Integer create(Session session, Employee e) 
			throws DataException {
		return (Integer) super.persist(null, e);
	}

	@Override
	public Boolean update(Connection conn, Employee e) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_QUERY);

			int index = setInsertValues(stmt, e);
			stmt.setInt(index++, e.getId());

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
	public Boolean updatePassword(Connection conn, Integer employeeId, String password) throws DataException {

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

	private static int setInsertValues(PreparedStatement stmt, Employee e) 
			throws SQLException {

		int index = 1;
		
		JDBCUtils.setNullable(stmt, e.getSupervisorId(), index++);
		stmt.setString(index++, e.getFirstName());
		stmt.setString(index++, e.getLastName1());
		JDBCUtils.setNullable(stmt, e.getLastName2(), index++);
		stmt.setString(index++, e.getDocumentTypeId());
		stmt.setString(index++, e.getDocumentNumber());
		stmt.setString(index++, e.getPhoneNumber());
		stmt.setString(index++, e.getEmail());
		stmt.setString(index++, e.getUsername());
		stmt.setString(index++, e.getEncryptedPassword());
		stmt.setTimestamp(index++, new java.sql.Timestamp(e.getCreationDate().getTime()));
		stmt.setString(index++, e.getIban());
		stmt.setString(index++, e.getBic());
		return index;
	}

	@Override
	public Boolean delete(Connection conn, Integer employeeId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_QUERY);

			int index = 1;
			stmt.setDate(index++, new java.sql.Date(new Date().getTime()));
			stmt.setInt(index++, employeeId);

			return stmt.executeUpdate() != 1 ? false : true;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private Employee loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		Employee e = new Employee();
		int i = 1;
		
		e.setId(rs.getInt(i++));
		e.setFirstName(rs.getString(i++));
		e.setLastName1(rs.getString(i++));
		e.setLastName2(rs.getString(i++));
		e.setDocumentTypeId(rs.getString(i++));
		e.setDocumentType(rs.getString(i++));
		e.setDocumentNumber(rs.getString(i++));
		e.setPhoneNumber(rs.getString(i++));
		e.setEmail(rs.getString(i++));
		e.setUsername(rs.getString(i++));
		e.setEncryptedPassword(rs.getString(i++));
		e.setCreationDate(rs.getTimestamp(i++));
		e.setIban(rs.getString(i++));
		e.setBic(rs.getString(i++));
		e.setSupervisorId(JDBCUtils.getNullableInt(rs, i++));
		e.setSupervisorFirstName(rs.getString(i++));
		e.setSupervisorLastName1(rs.getString(i++));
		e.setSupervisorLastName2(rs.getString(i++));
		
		e.setAddress(addressDAO.findByEmployee(conn, e.getId()));
		e.setDepartmentHistory(employeeDepartmentDAO.findByEmployee(conn, e.getId()));
		
		return e;
	}

}
