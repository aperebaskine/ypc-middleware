package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
import com.pinguela.yourpc.model.EmployeeDepartment;
import com.pinguela.yourpc.util.JDBCUtils;

public class EmployeeDepartmentDAOImpl 
implements EmployeeDepartmentDAO {
	
	private static Logger logger = LogManager.getLogger(EmployeeDepartmentDAOImpl.class);
	
	private static final String QUERY = 
			" SELECT ed.DEPARTMENT_ID, ed.START_DATE, ed.END_DATE"
			+ " FROM EMPLOYEE_DEPARTMENT ed"
			+ " WHERE ed.EMPLOYEE_ID = ?"
			+ " ORDER BY ed.END_DATE IS NULL DESC, ed.END_DATE DESC";

	@Override
	public List<EmployeeDepartment> findByEmployee(Connection conn, Integer employeeId) throws DataException {
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<EmployeeDepartment> results = new ArrayList<>();
		
		try {
			stmt = conn.prepareStatement(QUERY);
			
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
	
	private EmployeeDepartment loadNext(ResultSet rs) throws SQLException {
		
		EmployeeDepartment ed = new EmployeeDepartment();
		
		int i = 1;
		ed.setDepartmentId(rs.getString(i++));
		ed.setStartDate(rs.getDate(i++));
		JDBCUtils.getNullableDate(rs, i++);
		
		return ed;
	}

}
