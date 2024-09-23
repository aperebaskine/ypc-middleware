package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.model.Address;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.Employee;
import com.pinguela.yourpc.util.JDBCUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class AddressDAOImpl
extends AbstractDAO<Address>
implements AddressDAO {

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
		super(Address.class);
	}

	@Override
	public Address findById(Session session, Integer id)
			throws DataException {
			return super.findById(session, id);
	}

	@Override
	public Address findByEmployee(Session session, Integer employeeId)
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Address> query = builder.createQuery(getTargetClass());
			Root<Address> root = query.from(getTargetClass());
			
			Join<Address, Employee> employeeJoin = root.join("employee");
			query.where(builder.equal(employeeJoin.get("id"), employeeId));
			
			return session.createQuery(query).getSingleResult();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public List<Address> findByCustomer(Session session, Integer customerId)
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Address> query = builder.createQuery(getTargetClass());
			Root<Address> root = query.from(getTargetClass());
			
			Join<Address, Customer> employeeJoin = root.join("customer");
			query.where(builder.equal(employeeJoin.get("id"), customerId));
			
			return session.createQuery(query).getResultList();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public Integer create(Session session, Address a)
			throws DataException {
		return (Integer) super.persist(session, a);
	}

	@Override
	public Integer update(Connection conn, Address a)
			throws DataException {

		if (isOrderAddress(conn, a.getId())) { // Perform logical deletion on address linked to order(s)
			if (!a.equals(findById(conn, a.getId()))) {
				delete(conn, a.getId());
				return persist(conn, a);
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

	private Boolean isOrderAddress(Session session, Integer addressId) 
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
