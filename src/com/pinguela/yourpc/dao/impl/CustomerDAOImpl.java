package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.yourpc.dao.AddressDAO;
import com.pinguela.yourpc.dao.CustomerDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerCriteria;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class CustomerDAOImpl 
extends AbstractDAO<Integer, Customer>
implements CustomerDAO {

	private static final String UPDATE_QUERY =
			" UPDATE CUSTOMER"
					+ " SET FIRST_NAME = ?,"
					+ " LAST_NAME1 = ?,"
					+ " LAST_NAME2 = ?,"
					+ " DOCUMENT_TYPE_ID = ?,"
					+ " DOCUMENT_NUMBER = ?,"
					+ " PHONE = ?,"
					+ " EMAIL = ?,"
					+ " PASSWORD = ?,"
					+ " CREATION_DATE = ?"
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
	public Customer findById(Session session, Integer customerId) 
			throws DataException {
		return super.findById(session, customerId);
	}

	@Override
	public Customer findByEmail(Session session, String email) throws DataException {
		CustomerCriteria criteria = new CustomerCriteria();
		criteria.setEmail(email);
		return super.findBy(session, criteria).get(0);
	}

	@Override
	public List<Customer> findBy(Session session, CustomerCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}
	
	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, CriteriaQuery<Customer> query, 
			Root<Customer> root, AbstractCriteria<Customer> criteria) {
		
		CustomerCriteria customerCriteria = (CustomerCriteria) criteria;
		
		if (customerCriteria.getEmail() != null) {
			query.where(builder.equal(root.get("email"), customerCriteria.getEmail()));
		}
		if (customerCriteria.getFirstName() != null) {
			query.where(builder.like(root.get("name").get("firstName"), 
					SQLQueryUtils.wrapLike(customerCriteria.getFirstName())));
		}
		if (customerCriteria.getLastName1() != null) {
			query.where(builder.like(root.get("name").get("lastName1"), 
					SQLQueryUtils.wrapLike(customerCriteria.getLastName1())));
		}
		if (customerCriteria.getLastName2() != null) {
			query.where(builder.like(root.get("name").get("lastName2"), 
					SQLQueryUtils.wrapLike(customerCriteria.getLastName2())));
		}
		if (customerCriteria.getDocumentNumber() != null) {
			query.where(builder.equal(root.get("document").get("number"), 
					customerCriteria.getDocumentNumber()));
		}
		if (customerCriteria.getPhoneNumber() != null) {
			query.where(builder.equal(root.get("phone"), customerCriteria.getPhoneNumber()));
		}
	}

	@Override
	public Integer create(Session session, Customer c) 
			throws DataException {
		return create(session, c);
	}

	@Override
	public Boolean update(Connection conn, Customer c) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_QUERY);

			int index = setInsertValues(stmt, c);
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

	private static int setInsertValues(PreparedStatement stmt, Customer c) 
			throws SQLException {

		int index = 1;
		stmt.setString(index++, c.getFirstName());
		stmt.setString(index++, c.getLastName1());
		JDBCUtils.setNullable(stmt, c.getLastName2(), index++);
		stmt.setString(index++, c.getDocumentTypeId());
		stmt.setString(index++, c.getDocumentNumber());
		stmt.setString(index++, c.getPhoneNumber());
		stmt.setString(index++, c.getEmail());
		stmt.setString(index++, c.getEncryptedPassword());
		stmt.setTimestamp(index++, new java.sql.Timestamp(c.getCreationDate().getTime()));

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
