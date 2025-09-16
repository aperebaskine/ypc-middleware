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
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class CustomerDAOImpl implements CustomerDAO {

	private static final String SELECT_COLUMNS =
			" SELECT"
					+ " cu.ID,"
					+ " cu.FIRST_NAME,"
					+ " cu.LAST_NAME1,"
					+ " cu.LAST_NAME2,"
					+ " cu.DOCUMENT_TYPE_ID,"
					+ " dt.NAME,"
					+ " cu.DOCUMENT_NUMBER,"
					+ " cu.PHONE,"
					+ " cu.EMAIL,"
					+ " cu.PASSWORD,"
					+ " cu.CREATION_DATE";
	private static final String FROM_TABLE =
			" FROM CUSTOMER cu"
					+ " INNER JOIN DOCUMENT_TYPE dt"
					+ " ON dt.ID = cu.DOCUMENT_TYPE_ID";
	private static final String WHERE_ID =
			" WHERE cu.ID = ?";
	private static final String WHERE_EMAIL =
			" WHERE cu.EMAIL = ?";
	private static final String WHERE_DELETION_DATE =
			" AND cu.DELETION_DATE IS NULL";

	private static final String FINDBYID_QUERY = SELECT_COLUMNS +FROM_TABLE +WHERE_ID +WHERE_DELETION_DATE;
	private static final String FINDBYEMAIL_QUERY = SELECT_COLUMNS +FROM_TABLE +WHERE_EMAIL +WHERE_DELETION_DATE;

	public static final String EMAIL_EXISTS_QUERY =
			" SELECT cu.ID FROM CUSTOMER cu WHERE cu.EMAIL = ?";

	public static final String PHONE_NUMBER_EXISTS_QUERY =
			" SELECT cu.ID FROM CUSTOMER cu WHERE cu.PHONE = ?";

	private static final String CREATE_QUERY =
			"INSERT INTO CUSTOMER(FIRST_NAME,"
					+ " LAST_NAME1,"
					+ " LAST_NAME2,"
					+ " DOCUMENT_TYPE_ID,"
					+ " DOCUMENT_NUMBER,"
					+ " PHONE,"
					+ " CREATION_DATE,"
					+ " EMAIL,"
					+ " PASSWORD)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY =
			" UPDATE CUSTOMER"
					+ " SET FIRST_NAME = ?,"
					+ " LAST_NAME1 = ?,"
					+ " LAST_NAME2 = ?,"
					+ " DOCUMENT_TYPE_ID = ?,"
					+ " DOCUMENT_NUMBER = ?,"
					+ " PHONE = ?,"
					+ " PASSWORD = ?"
					+ " WHERE ID = ?";

	private static final String UPDATE_PASSWORD_QUERY =
			" UPDATE CUSTOMER"
					+ " SET PASSWORD = ?"
					+ " WHERE ID = ?";

	private static final String DELETE_QUERY =
			" UPDATE CUSTOMER SET DELETION_DATE = ? WHERE ID = ?";

	private static Logger logger = LogManager.getLogger(CustomerDAOImpl.class);
	private AddressDAO addressDAO = null;

	public CustomerDAOImpl() {
		addressDAO = new AddressDAOImpl();
	}

	@Override
	public Customer findById(Connection conn, Integer customerId) 
			throws DataException {

		if (customerId == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Customer c = null;

		try {
			stmt = conn.prepareStatement(FINDBYID_QUERY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, customerId);

			rs = stmt.executeQuery();
			if (rs.next()) {
				c = loadNext(conn, rs);
			} 
			return c;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Customer findByEmail(Connection conn, String email) throws DataException {

		if (email == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		Customer c = null;

		try {
			stmt = conn.prepareStatement(FINDBYEMAIL_QUERY);
			stmt.setString(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, email.toLowerCase());

			rs = stmt.executeQuery();
			if (rs.next()) {
				c = loadNext(conn, rs);
			} 
			return c;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public List<Customer> findBy(Connection conn, CustomerCriteria criteria) 
			throws DataException {

		String query = new StringBuilder(SELECT_COLUMNS)
				.append(FROM_TABLE)
				.append(buildWhereClause(criteria))
				.append(SQLQueryUtils.buildOrderByClause(criteria))
				.toString();

		PreparedStatement stmt = null;
		ResultSet rs = null;
		List<Customer> results = new ArrayList<Customer>();

		try {
			stmt = conn.prepareStatement(query);
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

	@Override
	public boolean emailExists(Connection conn, String email) throws DataException {

		if (email == null) {
			return false;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(EMAIL_EXISTS_QUERY);
			stmt.setString(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, email.toLowerCase());

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
	public boolean phoneNumberExists(Connection conn, String phoneNumber) throws DataException {

		if (phoneNumber == null) {
			return false;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(PHONE_NUMBER_EXISTS_QUERY);
			stmt.setString(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, phoneNumber.toLowerCase());

			rs = stmt.executeQuery();
			return rs.next();

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private StringBuilder buildWhereClause(CustomerCriteria criteria) {

		List<String> conditions = new ArrayList<String>();

		if (criteria.getEmail() != null) {
			conditions.add(" cu.EMAIL = ?");
		}
		if (criteria.getFirstName() != null) {
			conditions.add(" UPPER(cu.FIRST_NAME) LIKE ?");
		}
		if (criteria.getLastName1() != null) {
			conditions.add(" UPPER(cu.LAST_NAME1) LIKE ?");
		}
		if (criteria.getLastName2() != null) {
			conditions.add(" UPPER(cu.LAST_NAME2) LIKE ?");
		}
		if (criteria.getDocumentNumber() != null) {
			conditions.add(" cu.DOCUMENT_NUMBER = ?");
		}
		if (criteria.getPhoneNumber() != null) {
			conditions.add(" cu.PHONE = ?");
		}
		conditions.add(" cu.DELETION_DATE IS NULL");
		return SQLQueryUtils.buildWhereClause(conditions);
	}

	private void setSelectValues(PreparedStatement stmt, CustomerCriteria criteria) 
			throws SQLException {

		int i = 1;
		if (criteria.getEmail() != null) {
			stmt.setString(i++, criteria.getEmail().toLowerCase());
		}
		if (criteria.getFirstName() != null) {
			stmt.setString(i++, SQLQueryUtils.wrapLike(criteria.getFirstName()));
		}
		if (criteria.getLastName1() != null) {
			stmt.setString(i++, SQLQueryUtils.wrapLike(criteria.getLastName1()));
		}
		if (criteria.getLastName2() != null) {
			stmt.setString(i++, SQLQueryUtils.wrapLike(criteria.getLastName2()));
		}
		if (criteria.getDocumentNumber() != null) {
			stmt.setString(i++, criteria.getDocumentNumber());
		}
		if (criteria.getPhoneNumber() != null) {
			stmt.setString(i++, criteria.getPhoneNumber());
		}
	}

	@Override
	public Integer create(Connection conn, Customer c) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			c.setCreationDate(new Date());
			setInsertValues(stmt, c, true);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.next();
				c.setId(rs.getInt(JDBCUtils.GENERATED_KEY_INDEX));
				return c.getId();
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Boolean update(Connection conn, Customer c) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_QUERY);

			int index = setInsertValues(stmt, c, false);
			stmt.setInt(index++, c.getId());

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
	public Boolean updatePassword(Connection conn, Integer customerId, String password) throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_PASSWORD_QUERY);

			int i = 1;
			stmt.setString(i++, password);
			stmt.setInt(i++, customerId);

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

	private static int setInsertValues(PreparedStatement stmt, Customer c, boolean isNew) 
			throws SQLException {

		int index = 1;
		stmt.setString(index++, c.getFirstName());
		stmt.setString(index++, c.getLastName1());
		JDBCUtils.setNullable(stmt, c.getLastName2(), index++);
		stmt.setString(index++, c.getDocumentTypeId());
		stmt.setString(index++, c.getDocumentNumber());
		stmt.setString(index++, c.getPhoneNumber());

		if (isNew) {
			stmt.setTimestamp(index++, new java.sql.Timestamp(c.getCreationDate().getTime()));
			stmt.setString(index++, c.getEmail());
		}

		stmt.setString(index++, c.getEncryptedPassword());

		return index;
	}

	@Override
	public Boolean delete(Connection conn, Integer customerId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_QUERY);
			JDBCUtils.specifyLogicalDeletionParameters(stmt, customerId);
			return stmt.executeUpdate() != 1 ? false : true;

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt);
		}
	}

	private Customer loadNext(Connection conn, ResultSet rs) 
			throws SQLException, DataException {

		Customer cu = new Customer();
		int i = 1;

		cu.setId(rs.getInt(i++));
		cu.setFirstName(rs.getString(i++));
		cu.setLastName1(rs.getString(i++));
		cu.setLastName2(rs.getString(i++));
		cu.setDocumentTypeId(rs.getString(i++));
		cu.setDocumentType(rs.getString(i++));
		cu.setDocumentNumber(rs.getString(i++));
		cu.setPhoneNumber(rs.getString(i++));
		cu.setEmail(rs.getString(i++));
		cu.setEncryptedPassword(rs.getString(i++));
		cu.setCreationDate(rs.getTimestamp(i++));
		cu.setAddresses(addressDAO.findByCustomer(conn, cu.getId()));
		return cu;
	}

}
