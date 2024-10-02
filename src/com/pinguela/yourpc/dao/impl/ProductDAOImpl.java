package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.ProductDAO;
import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.AttributeValue;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductDAOImpl 
extends AbstractMutableDAO<Long, Product>
implements ProductDAO {
	
	private static final String PRODUCT_ALIAS = "p";
	
	private static final Map<String, Class<?>> PRODUCT_COLUMNS = defineProductColumns();
	
	private static final Map<String, String> PRODUCT_COLUMN_ALIASES = SQLQueryUtils.generateColumnAliases(Product.class, PRODUCT_COLUMNS.keySet());

	private static final String SELECT_QUERY_PLACEHOLDER =
			" SELECT %1$s"
					+ " FROM PRODUCT %2$s"
					+ " LEFT JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.PRODUCT_ID = %2$s.ID"
					+ " LEFT JOIN ATTRIBUTE_VALUE %3$s"
					+ " ON %3$s.ID = pav.ATTRIBUTE_VALUE_ID"
					+ "	LEFT JOIN ATTRIBUTE_TYPE %4$s"
					+ " on %4$s.ID = %3$s.ATTRIBUTE_TYPE_ID";
	
	private static final Map<String, Class<?>> defineProductColumns() {
	    Map<String, Class<?>> columns = new LinkedHashMap<>();
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "ID"), java.lang.Long.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "NAME"), java.lang.String.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "CATEGORY_ID"), java.lang.Short.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "DESCRIPTION"), java.lang.String.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "LAUNCH_DATE"), java.util.Date.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "DISCONTINUATION_DATE"), java.util.Date.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "STOCK"), java.lang.Integer.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "PURCHASE_PRICE"), java.lang.Double.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "SALE_PRICE"), java.lang.Double.class);
	    columns.put(SQLQueryUtils.applyTableAlias(PRODUCT_ALIAS, "REPLACEMENT_ID"), java.lang.Long.class);
	    return Collections.unmodifiableMap(columns);
	}

	private static final String getColumns() {
		Map<String, String> columns = new LinkedHashMap<String, String>();
		columns.putAll(PRODUCT_COLUMN_ALIASES);
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES);
		columns.putAll(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES);

		return SQLQueryUtils.createColumnClause(columns);
	}

	private static final String BASE_QUERY = 
			String.format(
					SELECT_QUERY_PLACEHOLDER, 
					getColumns(), 
					PRODUCT_ALIAS, 
					AttributeDAOImpl.ATTRIBUTE_VALUE_ALIAS,
					AttributeDAOImpl.ATTRIBUTE_ALIAS
					);

	private static final String SELECT_COLUMNS = 
			" SELECT"
					+ " p.ID,"
					+ " p.NAME,"
					+ " p.CATEGORY_ID,"
					+ " c.NAME,"
					+ " p.DESCRIPTION,"
					+ " p.LAUNCH_DATE,"
					+ " p.STOCK,"
					+ " p.PURCHASE_PRICE,"
					+ " p.SALE_PRICE,"
					+ " p.REPLACEMENT_ID,"
					+ " q.NAME";
	private static final String SELECT_RANGES =
			" SELECT"
					+ " MIN(p.STOCK),"
					+ " MAX(p.STOCK),"
					+ " MIN(p.SALE_PRICE),"
					+ " MAX(p.SALE_PRICE),"
					+ " MIN(p.LAUNCH_DATE),"
					+ " MAX(p.LAUNCH_DATE)";
	private static final String FROM_TABLE =
			" FROM PRODUCT p";
	private static final String JOIN_CATEGORY_AND_PRODUCT =
			" INNER JOIN CATEGORY c" 
					+ " ON c.ID = p.CATEGORY_ID"
					+ " LEFT JOIN PRODUCT q"
					+ " ON q.ID = p.REPLACEMENT_ID";
	private static final String JOIN_ATTRIBUTE =
			" INNER JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.PRODUCT_ID = p.ID"
					+ " INNER JOIN ATTRIBUTE_VALUE av"
					+ "	ON av.ID = pav.ATTRIBUTE_VALUE_ID"
					+ " INNER JOIN ATTRIBUTE_TYPE at"
					+ " ON at.ID = av.ATTRIBUTE_TYPE_ID";
	private static final String WHERE_ID_EQUALS = " WHERE p.ID = ?";
	private static final String AND_DISCONTINUATION_DATE = " AND p.DISCONTINUATION_DATE IS NULL";

	private static final String FINDBY_QUERY = SELECT_COLUMNS +FROM_TABLE +JOIN_CATEGORY_AND_PRODUCT;
	private static final String FINDBYID_QUERY = FINDBY_QUERY +WHERE_ID_EQUALS +AND_DISCONTINUATION_DATE;
	private static final String GET_RANGES_QUERY =
			SELECT_RANGES +FROM_TABLE;

	private static final String CREATE_QUERY = 
			" INSERT INTO PRODUCT(NAME,"
					+ " CATEGORY_ID,"
					+ " DESCRIPTION,"
					+ " LAUNCH_DATE,"
					+ " STOCK,"
					+ " PURCHASE_PRICE,"
					+ " SALE_PRICE)"
					+ " VALUES (?, ?, ?, ?, ?, ?, ?)";

	private static final String UPDATE_QUERY =
			" UPDATE PRODUCT"
					+ " SET NAME = ?,"
					+ " CATEGORY_ID = ?,"
					+ " DESCRIPTION = ?,"
					+ " LAUNCH_DATE = ?,"
					+ " STOCK = ?,"
					+ " PURCHASE_PRICE = ?,"
					+ " SALE_PRICE = ?"
					+ " WHERE ID = ?";
	
	private static final String DELETE_QUERY =
			" UPDATE PRODUCT"
					+ " SET DISCONTINUATION_DATE = ?"
					+ " WHERE ID = ?";

	private static Logger logger = LogManager.getLogger(ProductDAOImpl.class);
	private AttributeDAO attributeDAO = null;

	public ProductDAOImpl() {
		attributeDAO = new AttributeDAOImpl();
	}

	@Override
	public Long create(Session session, Product p) 
			throws DataException {
		return super.createEntity(session, p);
	}

	@Override
	public Boolean update(Session session, Product p) 
			throws DataException {
		return super.updateEntity(session, p);
	}
	
	@Override
	public Boolean delete(Session session, Long productId) 
			throws DataException {
		return super.deleteEntity(session, productId);
	}

	@Override
	public Product findById(Session session, Long id) 
			throws DataException {

		if (id == null) {
			return null;
		}

		try {
			NativeQuery<Object[]> query = 
					session.createNativeQuery(BASE_QUERY + " WHERE p.ID = :id", Object[].class);
			setScalars(query);
			ScrollableResults<Object[]> results = 
					query.setParameter("id", id).scroll();
			return loadNext(session, results);
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public Results<Product> findBy(Connection conn, ProductCriteria criteria, int pos, int pageSize) 
			throws DataException {

		StringBuilder query = new StringBuilder(FINDBY_QUERY).append(buildQueryClauses(criteria));
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			setSelectValues(stmt, criteria);

			rs = stmt.executeQuery();
			return loadResults(conn, rs, pos, pageSize);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public ProductRanges getRanges(Connection conn, ProductCriteria criteria) 
			throws DataException {
		
		ProductRanges ranges = new ProductRanges();

		StringBuilder query = new StringBuilder(GET_RANGES_QUERY).append(buildQueryClauses(criteria));
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			setSelectValues(stmt, criteria);

			rs = stmt.executeQuery();
			rs.next();
			
			int i = 1;
			ranges.setStockMin(rs.getInt(i++));
			ranges.setStockMax(rs.getInt(i++));
			ranges.setPriceMin(rs.getDouble(i++));
			ranges.setPriceMax(rs.getDouble(i++));
			ranges.setLaunchDateMin(rs.getDate(i++));
			ranges.setLaunchDateMax(rs.getDate(i++));
			
			return ranges;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}
	
	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Product> root,
			AbstractCriteria<Product> criteria) {
		// TODO Auto-generated method stub
		Predicate predicate = builder.equal(root.get("id"), criteria);
		predicate.
		return null;
	}

	private static StringBuilder buildQueryClauses(ProductCriteria criteria) {

		StringBuilder clauses = new StringBuilder();

		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {

			clauses.append(JOIN_ATTRIBUTE);
		}
		List<String> conditions = new ArrayList<String>();

		if (criteria.getName() != null) {
			conditions.add(" p.NAME LIKE ?");
		}
		if (criteria.getLaunchDateMin() != null) {	
			conditions.add(" p.LAUNCH_DATE >= ?");
		}
		if (criteria.getLaunchDateMax() != null) {	
			conditions.add(" p.LAUNCH_DATE <= ?");
		}
		if (criteria.getStockMin() != null) {	
			conditions.add(" p.STOCK >= ?");
		}
		if (criteria.getStockMax() != null) {	
			conditions.add(" p.STOCK <= ?");
		}
		if (criteria.getPriceMin() != null) {
			conditions.add(" p.SALE_PRICE >= ?");
		}
		if (criteria.getPriceMax() != null) {	
			conditions.add(" p.SALE_PRICE <= ?");
		}
		if (criteria.getCategoryId() != null) {	
			String categoryCondition = 
					new StringBuilder(" p.CATEGORY_ID")
					.append(SQLQueryUtils.buildPlaceholderComparisonClause(
							CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet())
							)
					.toString();
			conditions.add(categoryCondition);
		}
		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {	
			conditions.add(AttributeUtils.buildAttributeConditionClause(criteria.getAttributes()));
		}

		conditions.add(" p.DISCONTINUATION_DATE IS NULL");
		clauses.append(SQLQueryUtils.buildWhereClause(conditions));

		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {
			clauses.append(" GROUP BY p.ID HAVING COUNT(p.ID) = ?");
		}
		clauses.append(SQLQueryUtils.buildOrderByClause(criteria));

		return clauses;
	}

	private static int setSelectValues(PreparedStatement stmt, ProductCriteria criteria) throws SQLException {

		int i = 1;

		if (criteria.getName() != null) {
			stmt.setString(i++, SQLQueryUtils.wrapLike(criteria.getName()));
		}
		if (criteria.getLaunchDateMin() != null) {
			stmt.setDate(i++, new java.sql.Date(criteria.getLaunchDateMin().getTime()));
		}
		if (criteria.getLaunchDateMax() != null) {	
			stmt.setDate(i++, new java.sql.Date(criteria.getLaunchDateMax().getTime()));
		}
		if (criteria.getStockMin() != null) {		
			stmt.setInt(i++, criteria.getStockMin());
		}
		if (criteria.getStockMax() != null) {	
			stmt.setInt(i++, criteria.getStockMax());
		}
		if (criteria.getPriceMin() != null) {	
			stmt.setDouble(i++, criteria.getPriceMin());
		}
		if (criteria.getPriceMax() != null) {	
			stmt.setDouble(i++, criteria.getPriceMax());
		}
		if (criteria.getCategoryId() != null) {	
			for (Short categoryId : CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet()) {
				stmt.setShort(i++, categoryId);
			}
		}
		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {
			for (Attribute<?> attribute : criteria.getAttributes().values()) {
				stmt.setString(i++, attribute.getName());
				for (AttributeValue<?> valueContainer : attribute.getValuesByHandlingMode()) {
					stmt.setObject(i++, valueContainer.getValue(), AttributeUtils.getTargetSqlTypeIdentifier(attribute));
				}
			}
			stmt.setInt(i++, criteria.getAttributes().size());
		}
		return i;
	}

	private Results<Product> loadResults(Connection conn, ResultSet rs, int startPos, int pageSize)
			throws SQLException, DataException {

		Results<Product> results = new Results<Product>();
		results.setResultCount(JDBCUtils.getRowCount(rs));

		if (results.getResultCount() > 0 && pageSize > 0 && startPos > 0 && startPos <= results.getResultCount()) {
			int count = 0;
			rs.absolute(startPos);
			do {
				results.getPage().add(loadNext(conn, rs));
				count++;
			} while (count<pageSize && rs.next());	
		}

		return results;
	}

	private Product loadNext(Session session, ScrollableResults<Object[]> results) {
		
		Object[] row = results.get();

		Product p = new Product();
		int i = 1;

		p = new Product();
		p.setId((Long) row[i++]);
		p.setName((String) row[i++]);
		p.setCategory(session.getReference(Category.class, row[i++]));
		p.setDescription((String) row[i++]);
		p.setLaunchDate(rs.getDate(i++));
		p.setStock(rs.getInt(i++));
		p.setPurchasePrice(rs.getDouble(i++));
		p.setSalePrice(rs.getDouble(i++));
		p.setReplacementId(JDBCUtils.getNullableLong(rs, i++));
		p.setReplacementName(rs.getString(i++));
		p.setAttributes(attributeDAO.findByProduct(conn, p.getId()));
		return p;
	}
	
	private void setScalars(NativeQuery<Object[]> query) {
		for (String column : PRODUCT_COLUMNS.keySet()) {
			query.addScalar(PRODUCT_COLUMN_ALIASES.get(column), PRODUCT_COLUMNS.get(column));
		}
		
		for (String column : AttributeDAOImpl.ATTRIBUTE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES.get(column), AttributeDAOImpl.ATTRIBUTE_COLUMNS.get(column));
		}
		
		for (String column : AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES.get(column), AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMNS.get(column));
		}
	}

}
