package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.dao.EmployeeDAO;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.model.EmployeeCriteria;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class EmployeeDAOImpl implements EmployeeDAO {
	
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
		addressDAO = new AddressDAOImpl();
		employeeDepartmentDAO = new EmployeeDepartmentDAOImpl();
	}

	@Override
	public Employee findById(Connection conn, Integer employeeId) 
			throws DataException {
		
		if (employeeId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Employee e = null;

		try {
			stmt = conn.prepareStatement(FINDBYID_QUERY);

			int index = 1;
			stmt.setLong(index, employeeId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				e = loadNext(conn, rs);
			} 
			return e;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}

	}

	@Override
	public Employee findByUsername(Connection conn, String username) 
			throws DataException {
		
		if (username == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Employee e = null;

		try {
			stmt = conn.prepareStatement(FINDBYEMAIL_QUERY);

			int index = 1;
			stmt.setString(index, username.toLowerCase());

			rs = stmt.executeQuery();
			if (rs.next()) {
				e = loadNext(conn, rs);
			} 
			return e;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}

	}

	@Override
	public List<Employee> findBy(Connection conn, EmployeeCriteria criteria) 
			throws DataException {

		StringBuilder query = new StringBuilder(FINDBY_QUERY);
		if (criteria.getDepartmentId() != null) {
			query.append(JOIN_EMPLOYEE_DEPARTMENT);
		}
		query.append(buildWhereClause(criteria))
			.append(SQLQueryUtils.buildOrderByClause(criteria));

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Employee> results = new ArrayList<Employee>();

		try {
			stmt = conn.prepareStatement(query.toString());
			setSelectValues(stmt, criteria);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(conn, rs));	
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}

	}

	private StringBuilder buildWhereClause(EmployeeCriteria criteria) {

		List<String> conditions = new ArrayList<String>();

		if (criteria.getFirstName() != null) {
			conditions.add(" e.FIRST_NAME = ?");
		}
		if (criteria.getLastName1() != null) {
			conditions.add(" e.LAST_NAME1 = ?");
		}
		if (criteria.getLastName2() != null) {
			conditions.add(" e.LAST_NAME2 = ?");
		}
		if (criteria.getDocumentNumber() != null) {
			conditions.add(" e.DOCUMENT_NUMBER = ?");
		}
		if (criteria.getPhoneNumber() != null) {
			conditions.add(" e.PHONE = ?");
		}
		if (criteria.getEmail() != null) {
			conditions.add(" e.EMAIL = ?");
		}
		if (criteria.getUsername() != null) {
			conditions.add(" e.USERNAME = ?");
		}
		if (criteria.getDepartmentId() != null) {
			conditions.add(" ed.DEPARTMENT_ID = ?");
		}
		conditions.add(" e.TERMINATION_DATE IS NULL");
		return SQLQueryUtils.buildWhereClause(conditions);
	}

	private void setSelectValues(PreparedStatement stmt, EmployeeCriteria criteria) 
			throws SQLException {

		int index = 1;

		if (criteria.getFirstName() != null) {
			stmt.setString(index++, criteria.getFirstName());
		}
		if (criteria.getLastName1() != null) {
			stmt.setString(index++, criteria.getLastName1());
		}
		if (criteria.getLastName2() != null) {
			stmt.setString(index++, criteria.getLastName2());
		}
		if (criteria.getDocumentNumber() != null) {
			stmt.setString(index++, criteria.getDocumentNumber());
		}
		if (criteria.getPhoneNumber() != null) {
			stmt.setString(index++, criteria.getPhoneNumber());
		}
		if (criteria.getEmail() != null) {
			stmt.setString(index++, criteria.getEmail().toLowerCase());
		}
		if (criteria.getUsername() != null) {
			stmt.setString(index++, criteria.getUsername().toLowerCase());
		}
		if (criteria.getDepartmentId() != null) {
			stmt.setString(index++, criteria.getDepartmentId());
		}
	}

	@Override
	public Integer create(Connection conn, Employee e) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			e.setCreationDate(new Date());
			setInsertValues(stmt, e);

			int affectedRows = stmt.executeUpdate();

			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.first();
				e.setId(rs.getInt(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX));
				return e.getId();
			}
			
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
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
