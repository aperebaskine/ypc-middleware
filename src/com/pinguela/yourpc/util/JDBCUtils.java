package com.pinguela.yourpc.util;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.pinguela.yourpc.config.ConfigManager;

public class JDBCUtils {

	private static final String DB_DRIVER_CLASS_PNAME = "db.driver.class";
	private static final String DB_URL_PNAME = "db.url";
	private static final String USER_PNAME = "db.user";
	private static final String PASS_PNAME = "db.password";
	
	public static final int GENERATED_KEY_INDEX = 1;
	public static final int ID_CLAUSE_PARAMETER_INDEX = 1;
	
	public static final Boolean AUTO_COMMIT = Boolean.TRUE;
	public static final Boolean NO_AUTO_COMMIT = Boolean.FALSE;
	
	private static ComboPooledDataSource cpds = new ComboPooledDataSource();
	private static Logger logger = LogManager.getLogger(JDBCUtils.class);

	static {
		try {
			cpds.setDriverClass(ConfigManager.getValue(DB_DRIVER_CLASS_PNAME));
			cpds.setJdbcUrl(ConfigManager.getValue(DB_URL_PNAME));
			cpds.setUser(ConfigManager.getValue(USER_PNAME));
			cpds.setPassword(ConfigManager.getValue(PASS_PNAME));
		} catch (PropertyVetoException e) {
			logger.fatal(e);
		}
	}

	public static Connection getConnection() throws SQLException {
		Connection conn = cpds.getConnection();
		logger.info("Conexión establecida por el usuario {}.", conn.getMetaData().getUserName());
		return conn;
	}
	
	public static void setLoginParameters(String dbUrl, String user, String password) throws SQLException {
		
		cpds.setJdbcUrl(dbUrl);
		cpds.setUser(user);
		cpds.setPassword(password);
	}

	public static final void setNullable(PreparedStatement stmt, Long value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.BIGINT);
		} else {
			stmt.setLong(index, value);
		}
	}

	public static final void setNullable(PreparedStatement stmt, String value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.VARCHAR);
		} else {
			stmt.setString(index, value);
		}
	}

	public static final void setNullable(PreparedStatement stmt, Integer value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.INTEGER);
		} else {
			stmt.setInt(index, value);
		}
	}

	public static final void setNullable(PreparedStatement stmt, Date value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.TIMESTAMP);
		} else {
			stmt.setDate(index, new java.sql.Date(value.getTime()));
		}
	}

	public static final void setNullable(PreparedStatement stmt, Double value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.DECIMAL);
		} else {
			stmt.setDouble(index, value);
		}
	}

	public static final void setNullable(PreparedStatement stmt, Boolean value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.TINYINT);
		} else {
			stmt.setBoolean(index, value);
		}
	}

	public static final void setNullable(PreparedStatement stmt, Short value, int index)
			throws SQLException {

		if (value == null) {
			stmt.setNull(index, Types.SMALLINT);
		} else {
			stmt.setShort(index, value);
		}
	}

	/**
	 * Returns a long value from a null-able database column.
	 * @param rs ResultSet that is assumed to have its cursor already placed on the row
	 * that the caller method is retrieving the null-able value from.
	 * 
	 * @param index Index of the value to retrieve.
	 * @return null if the retrieved value is SQL NULL, else the retrieved value.
	 * @throws SQLException propagated from {@link java.sql.ResultSet}
	 */
	public static final Long getNullableLong(ResultSet rs, int index) 
			throws SQLException {
		
		Long value = rs.getLong(index);
		return rs.wasNull() ? null : value;
	}
	
	public static final Long getNullableLong(ResultSet rs, String columnName) 
			throws SQLException {
		
		Long value = rs.getLong(columnName);
		return rs.wasNull() ? null : value;
	}
	
	/**
	 * Returns an integer value from a null-able database column.
	 * @param rs ResultSet that is assumed to have its cursor already placed on the row
	 * that the caller method is retrieving the null-able value from.
	 * 
	 * @param index Index of the value to retrieve.
	 * @return null if the retrieved value is NULL, else the retrieved value.
	 * @throws SQLException propagated from {@link java.sql.ResultSet}
	 */
	public static final Integer getNullableInt(ResultSet rs, int index) 
			throws SQLException {

		Integer value = rs.getInt(index);
		return rs.wasNull() ? null : value;
	}
	
	/**
	 * Returns a short value from a null-able database column.
	 * @param rs ResultSet that is assumed to have its cursor already placed on the row
	 * that the caller method is retrieving the null-able value from.
	 * 
	 * @param index Index of the value to retrieve.
	 * @return null if the retrieved value is NULL, else the retrieved value.
	 * @throws SQLException propagated from {@link java.sql.ResultSet}
	 */
	public static final Short getNullableShort(ResultSet rs, int index) 
			throws SQLException {

		Short value = rs.getShort(index);
		return rs.wasNull() ? null : value;
	}

	public static final void close(Connection conn, boolean commit) {

		if (conn != null) {
			try {
				if (commit) {
					conn.commit();
					logger.info("Transacción completada.");
				} else {
					conn.rollback();
					logger.warn("Transacción anulada.");
				}
				conn.close();
				logger.info("Conexión cerrada.");
			} catch (SQLException sqle) {
				logger.error("Error al cerrar conexión.", sqle);
			}
		}
	}

	public static final void close(Connection conn) {

		if (conn != null) {
			try {
				conn.close();
				logger.info("Conexión cerrada.");
			} catch (SQLException sqle) {
				logger.error("Error al cerrar conexión.", sqle);
			}
		}
	}

	public static final void close(Statement stmt) {

		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException sqle) {
				logger.error("Error al cerrar Statement.", sqle);
			}
		}
	}

	public static final void close(ResultSet rs) {

		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException sqle) {
				logger.error("Error al cerrar ResultSet.", rs, sqle);
			}
		}
	}

	public static final void close(Statement stmt, ResultSet rs) {
		close(rs);
		close(stmt);
	}

	public static final void close(Statement stmt, List<ResultSet> rsList) {
		closeAllResultSets(rsList);
		close(stmt);
	}
	
	public static final void close(List<Statement> stmtList, List<ResultSet> rsList) {
		closeAllResultSets(rsList);
		closeAllStatements(stmtList);
	}

	private static final void closeAllStatements(List<Statement> stmtList) {
		
		if (stmtList == null || stmtList.size() == 0) {
			return;
		}
		for (Statement stmt : stmtList) {
			close(stmt);
		}
	}

	private static final void closeAllResultSets(List<ResultSet> rsList) {
		
		if (rsList == null || rsList.size() == 0) {
			return;
		}
		for (ResultSet rs : rsList) {
			close(rs);
		}
	}

	/** Specify parameters for a logical deletion database query. Assumes the query inserted into the
	 * prepared statement has two values, the first one being a deletion date and the second one being
	 * the primary key.
	 * @param stmt PreparedStatement object with unspecified values
	 * @param key value to insert as parameter for the primary key
	 * @throws SQLException propagated from the PreparedStatement.
	 */
	public static final void specifyLogicalDeletionParameters(PreparedStatement stmt, Long key) 
			throws SQLException {
		
		int index = 1;
		stmt.setTimestamp(index++, new java.sql.Timestamp(System.currentTimeMillis()));
		stmt.setLong(index++, key);
	}

	/** Specify parameters for a logical deletion database query. Assumes the query inserted into the
	 * prepared statement has two values, the first one being a deletion date and the second one being
	 * the primary key.
	 * @param stmt PreparedStatement object with unspecified values
	 * @param key value to insert as parameter for the primary key
	 * @throws SQLException propagated from the PreparedStatement.
	 */
	public static final void specifyLogicalDeletionParameters(PreparedStatement stmt, Integer key) 
			throws SQLException {
		specifyLogicalDeletionParameters(stmt, Long.valueOf(key));
	}

	public static final int getRowCount(ResultSet rs)
			throws SQLException {
		
		int rowCount = 0;
		int currentRow = rs.getRow();

		if (rs.last()) {
			rowCount = rs.getRow();
			rs.absolute(currentRow);
		}
		return rowCount;
	}

}
