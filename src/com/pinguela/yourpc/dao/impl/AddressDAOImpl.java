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
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.util.JDBCUtils;

public class AddressDAOImpl implements AddressDAO {

	private static final String IS_DEFAULT_COLUMN = "IS_DEFAULT";
	private static final String IS_BILLING_COLUMN = "IS_BILLING";

	private static final String SELECT_COLUMNS =
			" SELECT a.ID,"
					+ " a.CUSTOMER_ID,"
					+ " a.EMPLOYEE_ID,"
					+ " a.STREET_NAME,"
					+ " a.STREET_NUMBER,"
					+ " a.FLOOR,"
					+ " a.DOOR,"
					+ " a.ZIP_CODE,"
					+ " a.CITY_ID,"
					+ " ci.NAME,"
					+ " ci.PROVINCE_ID,"
					+ " pr.NAME,"
					+ " pr.COUNTRY_ID,"
					+ " co.NAME,"
					+ " a.IS_DEFAULT,"
					+ " a.IS_BILLING,"
					+ " a.CREATION_DATE";
	private static final String FROM_TABLE = 
			" FROM ADDRESS a"
					+ " INNER JOIN CITY ci"
					+ " ON a.CITY_ID = ci.ID"
					+ " INNER JOIN PROVINCE pr"
					+ " ON ci.PROVINCE_ID = pr.ID"
					+ "	INNER JOIN COUNTRY co"
					+ " ON pr.COUNTRY_ID = co.ID";

	private static final String FINDBY_QUERY = SELECT_COLUMNS +FROM_TABLE;
	private static final String FINDBY_QUERY_ID_PARAMETER = " WHERE a.ID = ?";
	private static final String FINDBY_QUERY_CUSTOMER_ID_PARAMETER = " WHERE a.CUSTOMER_ID = ?";
	private static final String FINDBY_QUERY_EMPLOYEE_ID_PARAMETER = " WHERE a.EMPLOYEE_ID = ?";
	private static final String FINDBY_QUERY_UNDELETED_PARAMETER = " AND DELETION_DATE IS NULL";

	private static final String FIND_BY_ID_QUERY = 
			FINDBY_QUERY 
			+FINDBY_QUERY_ID_PARAMETER 
			+FINDBY_QUERY_UNDELETED_PARAMETER;
	private static final String FIND_BY_EMPLOYEE_QUERY = 
			FINDBY_QUERY
			+FINDBY_QUERY_EMPLOYEE_ID_PARAMETER
			+FINDBY_QUERY_UNDELETED_PARAMETER;
	private static final String FIND_BY_CUSTOMER_QUERY = 
			FINDBY_QUERY
			+FINDBY_QUERY_CUSTOMER_ID_PARAMETER
			+FINDBY_QUERY_UNDELETED_PARAMETER;

	private static final String CREATE_QUERY = 
			" INSERT INTO ADDRESS(CUSTOMER_ID,"
					+ " EMPLOYEE_ID,"
					+ " STREET_NAME,"
					+ " STREET_NUMBER,"
					+ " FLOOR,"
					+ " DOOR,"
					+ " ZIP_CODE,"
					+ " CITY_ID,"
					+ " IS_DEFAULT,"
					+ " IS_BILLING,"
					+ " CREATION_DATE)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY =
			" UPDATE ADDRESS SET CUSTOMER_ID = ?,"
					+ " EMPLOYEE_ID = ?,"
					+ " STREET_NAME = ?,"
					+ " STREET_NUMBER = ?,"
					+ " FLOOR = ?,"
					+ " DOOR = ?,"
					+ " ZIP_CODE = ?,"
					+ " CITY_ID = ?,"
					+ " IS_DEFAULT = ?,"
					+ " IS_BILLING = ?,"
					+ " CREATION_DATE = ?"
					+ " WHERE ID = ?";

	private static final String IS_ORDER_ADDRESS_QUERY =
			" SELECT co.ID"
					+ " FROM CUSTOMER_ORDER co"
					+ " WHERE BILLING_ADDRESS_ID = ?"
					+ " OR SHIPPING_ADDRESS_ID = ?";

	private static final String SET_DEFAULT_PLACEHOLDER_QUERY =
			" UPDATE ADDRESS a"
					+ "	INNER JOIN ADDRESS b"
					+ "	ON a.CUSTOMER_ID = b.CUSTOMER_ID"
					+ " SET a.%1$s = (a.ID = ?)"
					+ " WHERE b.ID = ?";

	private static final String DELETE_FROM_TABLE = " UPDATE ADDRESS SET DELETION_DATE = ?";
	private static final String DELETE_QUERY_ID_PARAMETER = " WHERE ID = ?";
	private static final String DELETE_QUERY_CUSTOMER_ID_PARAMETER = " WHERE CUSTOMER_ID = ?";
	private static final String DELETE_QUERY_UNDELETED_PARAMETER = " AND DELETION_DATE IS NULL";

	private static final String DELETE_QUERY =
			DELETE_FROM_TABLE +DELETE_QUERY_ID_PARAMETER +DELETE_QUERY_UNDELETED_PARAMETER;
	private static final String DELETE_BY_CUSTOMER_QUERY =
			DELETE_FROM_TABLE +DELETE_QUERY_CUSTOMER_ID_PARAMETER +DELETE_QUERY_UNDELETED_PARAMETER;

	private static Logger logger = LogManager.getLogger(AddressDAOImpl.class);

	public AddressDAOImpl() {
	}

	@Override
	public Address findById(Connection conn, Integer id)
			throws DataException {

		if (id == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Address a = null;

		try {
			stmt = conn.prepareStatement(FIND_BY_ID_QUERY, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, id);

			rs = stmt.executeQuery();
			if (rs.next()) {
				a = loadNext(rs);
			}
			return a;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Address findByEmployee(Connection conn, Integer employeeId)
			throws DataException {

		if (employeeId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Address a = null;

		try {
			stmt = conn.prepareStatement(FIND_BY_EMPLOYEE_QUERY, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, employeeId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				a = loadNext(rs);
			}
			return a;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public List<Address> findByCustomer(Connection conn, Integer customerId)
			throws DataException {

		if (customerId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Address> results = new ArrayList<Address>();

		try {
			stmt = conn.prepareStatement(FIND_BY_CUSTOMER_QUERY, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, customerId);

			rs = stmt.executeQuery();
			while (rs.next()) {
				results.add(loadNext(rs));
			}
			return results;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Integer create(Connection conn, Address a)
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			if (a.getEmployeeId() != null) {
				Address old = findByEmployee(conn, a.getEmployeeId());
				if (old != null) {
					delete(conn, old.getId());
				}
			}

			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			a.setCreationDate(new Date());
			setInsertValues(stmt, a);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.next();
				a.setId(rs.getInt(JDBCUtils.GENERATED_KEY_INDEX));
				updateDefaultAndBilling(conn, a);
				return a.getId();
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Integer update(Connection conn, Address a)
			throws DataException {

		if (isOrderAddress(conn, a.getId())) { // Perform logical deletion on address linked to order(s)
			if (!a.equals(findById(conn, a.getId()))) {
				delete(conn, a.getId());
				return create(conn, a);
			}
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(UPDATE_QUERY);
			int i = setInsertValues(stmt, a);
			stmt.setInt(i++, a.getId());

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			}
			updateDefaultAndBilling(conn, a);
			return a.getId();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	private void updateDefaultAndBilling(Connection conn, Address a) throws DataException {
		if (a.getCustomerId() == null) {
			return;
		}			
		if (a.isDefault()) {
			setDefault(conn, a.getId());
		}
		if (a.isBilling()) {
			setBilling(conn, a.getId());
		}
	}

	private Boolean isOrderAddress(Connection conn, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(IS_ORDER_ADDRESS_QUERY);
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);

			rs = stmt.executeQuery();
			return rs.next();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}	
	}

	@Override
	public void setDefault(Connection conn, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(String.format(SET_DEFAULT_PLACEHOLDER_QUERY, IS_DEFAULT_COLUMN));
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	@Override
	public void setBilling(Connection conn, Integer addressId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(String.format(SET_DEFAULT_PLACEHOLDER_QUERY, IS_BILLING_COLUMN));
			int i = 1;
			stmt.setInt(i++, addressId);
			stmt.setInt(i++, addressId);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private int setInsertValues(PreparedStatement stmt, Address a) 
			throws SQLException {

		int index = 1;

		JDBCUtils.setNullable(stmt, a.getCustomerId(), index++);
		JDBCUtils.setNullable(stmt, a.getEmployeeId(), index++);
		stmt.setString(index++, a.getStreetName());
		JDBCUtils.setNullable(stmt, a.getStreetNumber(), index++);
		JDBCUtils.setNullable(stmt, a.getFloor(), index++);
		JDBCUtils.setNullable(stmt, a.getDoor(), index++);
		stmt.setString(index++, a.getZipCode());
		stmt.setInt(index++, a.getCityId());
		JDBCUtils.setNullable(stmt, a.isDefault(), index++);
		JDBCUtils.setNullable(stmt, a.isBilling(), index++);
		stmt.setTimestamp(index++, new java.sql.Timestamp(a.getCreationDate().getTime()));
		return index;
	}

	@Override
	public Boolean delete(Connection conn, Integer id)
			throws DataException {
		
		if (id == null) {
			return false;
		}

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_QUERY);
			JDBCUtils.specifyLogicalDeletionParameters(stmt, id);
			return stmt.executeUpdate() > 0;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	public Boolean deleteByCustomer(Connection conn, Integer customerId)
			throws DataException {
		
		if (customerId == null) {
			return false;
		}

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_BY_CUSTOMER_QUERY);
			JDBCUtils.specifyLogicalDeletionParameters(stmt, customerId);
			return stmt.executeUpdate() > 0;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private Address loadNext(ResultSet rs) 
			throws SQLException {

		Address a = new Address();
		int i = 1;

		a.setId(rs.getInt(i++));
		a.setCustomerId(JDBCUtils.getNullableInt(rs, i++));
		a.setEmployeeId(JDBCUtils.getNullableInt(rs, i++));
		a.setStreetName(rs.getString(i++));
		a.setStreetNumber(JDBCUtils.getNullableShort(rs, i++));
		a.setFloor(JDBCUtils.getNullableShort(rs, i++));
		a.setDoor(rs.getString(i++));
		a.setZipCode(rs.getString(i++));
		a.setCityId(rs.getInt(i++));
		a.setCity(rs.getString(i++));
		a.setProvinceId(rs.getInt(i++));
		a.setProvince(rs.getString(i++));
		a.setCountryId(rs.getString(i++));
		a.setCountry(rs.getString(i++));
		a.setIsDefault(rs.getBoolean(i++));
		a.setIsBilling(rs.getBoolean(i++));
		a.setCreationDate(rs.getTimestamp(i++));
		return a;
	}

}
