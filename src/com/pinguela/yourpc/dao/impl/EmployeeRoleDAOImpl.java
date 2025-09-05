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
import com.pinguela.yourpc.dao.EmployeeRoleDAO;
import com.pinguela.yourpc.model.EmployeeRole;
import com.pinguela.yourpc.util.JDBCUtils;

public class EmployeeRoleDAOImpl 
implements EmployeeRoleDAO {
	
	private static Logger logger = LogManager.getLogger(EmployeeRoleDAOImpl.class);
	
	private static final String FINDBYEMPLOYEE_QUERY = 
			" SELECT er.ROLE_ID, er.START_DATE, er.END_DATE"
			+ " FROM EMPLOYEE_ROLE er"
			+ " WHERE er.EMPLOYEE_ID = ?"
			+ " ORDER BY er.END_DATE IS NULL DESC, er.END_DATE DESC";
	
	private static final String ASSIGNTOEMPLOYEE_QUERY = 
			" INSERT INTO EMPLOYEE_ROLE(EMPLOYEE_ID, ROLE_ID, START_DATE)"
			+ " VALUES (?, ?, ?)";
	
	private static final String UNASSIGN_QUERY =
			" UPDATE EMPLOYEE_ROLE SET END_DATE = ? WHERE EMPLOYEE_ID = ? AND END_DATE IS NULL";

	@Override
	public List<EmployeeRole> findByEmployee(Connection conn, Integer employeeId) throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EmployeeRole> results = new ArrayList<>();
		
		try {
			stmt = conn.prepareStatement(FINDBYEMPLOYEE_QUERY);
			
			int i = 1;
			stmt.setInt(i++, employeeId);
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	private EmployeeRole loadNext(ResultSet rs) throws SQLException {
		
		EmployeeRole ed = new EmployeeRole();
		
		int i = 1;
		ed.setRoleId(rs.getString(i++));
		ed.setStartDate(rs.getDate(i++));
		JDBCUtils.getNullableDate(rs, i++);
		
		return ed;
	}
	
	@Override
	public Integer assignToEmployee(Connection conn, Integer employeeId, String roleId) throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			unassign(conn, employeeId);
			stmt = conn.prepareStatement(ASSIGNTOEMPLOYEE_QUERY, Statement.RETURN_GENERATED_KEYS);
			
			int i = 1;
			stmt.setInt(i++, employeeId);
			stmt.setString(i++, roleId);
			stmt.setDate(i++, new java.sql.Date(new Date().getTime()));
			
			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				DataException e = new DataException(ErrorCodes.INSERT_FAILED);
				logger.error(e.getMessage(), e);
				throw e;
			}
			
			rs = stmt.getGeneratedKeys();
			rs.next();
			return rs.getInt(1);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	public Boolean unassign(Connection conn, Integer employeeId) throws DataException {
	
		PreparedStatement stmt = null;
		
		try {
			stmt = conn.prepareStatement(UNASSIGN_QUERY);
			
			int i = 1;
			stmt.setDate(i++, new java.sql.Date(new Date().getTime()));
			stmt.setInt(i++, employeeId);
			
			return stmt.executeUpdate() > 0;
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

}
