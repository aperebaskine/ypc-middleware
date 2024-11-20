package com.pinguela.yourpc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ErrorCodes;
import com.pinguela.LogMessages;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.ProductDAO;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.AttributeDTO;
import com.pinguela.yourpc.model.dto.AttributeValueDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.model.dto.ProductDTO;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.JDBCUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

public class ProductDAOImpl 
implements ProductDAO {

	private static final String SELECT_COLUMNS = 
			" SELECT"
					+ " p.ID,"
					+ " pl.LOCALE_ID,"
					+ " pl.NAME,"
					+ " p.CATEGORY_ID,"
					+ " cl.NAME,"
					+ " pl.DESCRIPTION,"
					+ " p.LAUNCH_DATE,"
					+ " p.STOCK,"
					+ " p.PURCHASE_PRICE,"
					+ " p.SALE_PRICE,"
					+ " p.REPLACEMENT_ID,"
					+ " ql.NAME";
	private static final String SELECT_RANGES =
			" SELECT"
					+ " MIN(p.STOCK),"
					+ " MAX(p.STOCK),"
					+ " MIN(p.SALE_PRICE),"
					+ " MAX(p.SALE_PRICE),"
					+ " MIN(p.LAUNCH_DATE),"
					+ " MAX(p.LAUNCH_DATE)";
	private static final String FROM_TABLE =
			" FROM PRODUCT p"
					+" INNER JOIN PRODUCT_LOCALE pl"
					+ " ON pl.PRODUCT_ID = p.ID";
	private static final String LOCALE_ID_JOIN_CONDITION =
			" AND pl.LOCALE_ID = ?";

	private static final String JOIN_CATEGORY_AND_PRODUCT =
			" INNER JOIN CATEGORY c" 
					+ " ON c.ID = p.CATEGORY_ID"
					+ " INNER JOIN CATEGORY_LOCALE cl"
					+ " ON cl.CATEGORY_ID = c.ID"
					+ " AND cl.LOCALE_ID = ?"
					+ " LEFT JOIN PRODUCT q"
					+ " ON q.ID = p.REPLACEMENT_ID"
					+ " LEFT JOIN PRODUCT_LOCALE ql"
					+ " ON q.ID = ql.PRODUCT_ID"
					+ " AND ql.LOCALE_ID = ?";

	private static final String JOIN_ATTRIBUTE =
			" INNER JOIN PRODUCT_ATTRIBUTE_VALUE pav"
					+ " ON pav.PRODUCT_ID = p.ID"
					+ " INNER JOIN ATTRIBUTE_VALUE av"
					+ "	ON av.ID = pav.ATTRIBUTE_VALUE_ID"
					+ " INNER JOIN ATTRIBUTE_TYPE at"
					+ " ON at.ID = av.ATTRIBUTE_TYPE_ID"
					+ " INNER JOIN ATTRIBUTE_TYPE_LOCALE atl"
					+ " ON atl.ATTRIBUTE_TYPE_ID = at.ID"
					+ " AND atl.LOCALE_ID = ?";

	private static final String WHERE_ID_EQUALS = " WHERE p.ID = ? AND p.DISCONTINUATION_DATE IS NULL";

	private static final String LOAD_PRODUCT_LOCALE_QUERY = 
			" SELECT pl.LOCALE_ID, pl.NAME, pl.DESCRIPTION"
					+ " FROM PRODUCT_LOCALE pl"
					+ " WHERE pl.PRODUCT_ID = ?";

	private static final String CREATE_QUERY = 
			" INSERT INTO PRODUCT("
					+ " CATEGORY_ID,"
					+ " LAUNCH_DATE,"
					+ " STOCK,"
					+ " PURCHASE_PRICE,"
					+ " SALE_PRICE)"
					+ " VALUES"
					+ " (?, ?, ?, ?, ?)";

	private static final String CREATE_I18N_QUERY = 
			" INSERT INTO PRODUCT_LOCALE (PRODUCT_ID, LOCALE_ID, NAME, DESCRIPTION)";

	private static final String UPDATE_QUERY =
			" UPDATE PRODUCT SET"
					+ " CATEGORY_ID = ?,"
					+ " LAUNCH_DATE = ?,"
					+ " STOCK = ?,"
					+ " PURCHASE_PRICE = ?,"
					+ " SALE_PRICE = ?"
					+ " WHERE ID = ?";

	private static final String DELETE_QUERY =
			" UPDATE PRODUCT"
					+ " SET DISCONTINUATION_DATE = ?"
					+ " WHERE ID = ?";

	private static final String DELETE_LOCALE_QUERY = 
			" DELETE FROM PRODUCT_LOCALE WHERE PRODUCT_ID = ?";

	private static Logger logger = LogManager.getLogger(ProductDAOImpl.class);
	private AttributeDAO attributeDAO = null;

	public ProductDAOImpl() {
		attributeDAO = new AttributeDAOImpl();
	}

	@Override
	public Long create(Connection conn, ProductDTO p) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(CREATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			setInsertValues(stmt, p);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != 1) {
				logger.error(LogMessages.INSERT_FAILED, p);
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				rs = stmt.getGeneratedKeys();
				rs.next();
				p.setId(rs.getLong(JDBCUtils.GENERATED_KEY_INDEX));

				createLocale(conn, p);
				attributeDAO.assignToProduct(conn, p);

				return p.getId();
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private boolean createLocale(Connection conn, ProductDTO p) 
			throws DataException {
		
		String query = new StringBuffer(CREATE_I18N_QUERY)
				.append(SQLQueryUtils.buildPlaceholderValuesClause(p.getNameI18n().size(), 4))
				.toString();

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query);
			setI18nInsertValues(stmt, p);

			int affectedRows = stmt.executeUpdate();
			if (affectedRows != p.getNameI18n().size()) {
				logger.error(LogMessages.INSERT_FAILED, p);
				throw new DataException(ErrorCodes.INSERT_FAILED);
			} else {
				return true;
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Boolean update(Connection conn, ProductDTO p) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(UPDATE_QUERY, Statement.RETURN_GENERATED_KEYS);
			int index = setInsertValues(stmt, p);
			stmt.setLong(index++, p.getId());

			if (stmt.executeUpdate() != 1) {
				throw new DataException(ErrorCodes.UPDATE_FAILED);
			} else {
				deleteLocale(conn, p.getId());
				createLocale(conn, p);
				attributeDAO.assignToProduct(conn, p);
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
	public Boolean delete(Connection conn, Long productId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_QUERY, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			stmt.setTimestamp(index++, new java.sql.Timestamp(new Date().getTime()));
			stmt.setLong(index++, productId);

			if (stmt.executeUpdate() != 1) {
				return false;
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

	private boolean deleteLocale(Connection conn, Long productId) 
			throws DataException {

		PreparedStatement stmt = null;

		try {
			stmt = conn.prepareStatement(DELETE_LOCALE_QUERY, Statement.RETURN_GENERATED_KEYS);
			stmt.setLong(1, productId);

			if (stmt.executeUpdate() < 1) {
				return false;
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

	private int setInsertValues(PreparedStatement stmt, ProductDTO p) throws SQLException {

		int i = 1;

		stmt.setShort(i++, p.getCategoryId());
		JDBCUtils.setNullable(stmt, p.getLaunchDate(), i++);
		stmt.setInt(i++, p.getStock());
		stmt.setDouble(i++, p.getPurchasePrice());
		stmt.setDouble(i++, p.getSalePrice());
		return i;
	}

	private int setI18nInsertValues(PreparedStatement stmt, ProductDTO p) 
			throws SQLException {

		int i = 1;
		for (Locale locale : p.getNameI18n().keySet()) {
			stmt.setLong(i++, p.getId());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, p.getNameI18n().get(locale));
			stmt.setString(i++, p.getDescriptionI18n().get(locale));
		}
		return i;
	}

	@Override
	public ProductDTO findById(Connection conn, Long id, Locale locale) 
			throws DataException {

		if (id == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		ProductDTO p = null;

		try {
			stmt = conn.prepareStatement(SELECT_COLUMNS +FROM_TABLE +LOCALE_ID_JOIN_CONDITION +JOIN_CATEGORY_AND_PRODUCT +WHERE_ID_EQUALS);
			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setLong(i++, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				p = loadNext(conn, rs, locale);
			}
			return p;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public LocalizedProductDTO findByIdLocalized(Connection conn, Long id, Locale locale) throws DataException {

		if (id == null) {
			return null;
		}

		PreparedStatement stmt = null;
		ResultSet rs = null;
		LocalizedProductDTO p = null;

		try {
			stmt = conn.prepareStatement(SELECT_COLUMNS +FROM_TABLE 
					+LOCALE_ID_JOIN_CONDITION +JOIN_CATEGORY_AND_PRODUCT +WHERE_ID_EQUALS);

			int i = 1;
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setLong(i++, id);
			rs = stmt.executeQuery();
			if (rs.next()) {
				p = loadNextLocalized(conn, rs, locale);
			}
			return p;
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public Results<LocalizedProductDTO> findBy(Connection conn, ProductCriteria criteria, Locale locale, int pos, int pageSize) 
			throws DataException {

		StringBuilder query = new StringBuilder(SELECT_COLUMNS +FROM_TABLE +LOCALE_ID_JOIN_CONDITION +JOIN_CATEGORY_AND_PRODUCT);
		query.append(buildQueryClauses(criteria));
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			setSelectValues(stmt, criteria, locale);

			rs = stmt.executeQuery();
			return loadResults(conn, rs, locale, pos, pageSize);

		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	@Override
	public ProductRanges getRanges(Connection conn, ProductCriteria criteria, Locale locale) 
			throws DataException {

		ProductRanges ranges = new ProductRanges();

		StringBuilder query = new StringBuilder(SELECT_RANGES +FROM_TABLE)
				.append(buildQueryClauses(criteria));
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(query.toString(),
					ResultSet.TYPE_SCROLL_INSENSITIVE, 
					ResultSet.CONCUR_READ_ONLY);
			setSelectValues(stmt, criteria, locale);

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

	private static StringBuilder buildQueryClauses(ProductCriteria criteria) {

		StringBuilder clauses = new StringBuilder();

		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {

			clauses.append(JOIN_ATTRIBUTE);
		}
		List<String> conditions = new ArrayList<String>();

		if (criteria.getName() != null) {
			conditions.add(" pl.NAME LIKE ?");
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

	private static int setSelectValues(PreparedStatement stmt, ProductCriteria criteria, Locale locale) throws SQLException {

		int i = 1;

		if (locale != null) {
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			stmt.setString(i++, locale.toLanguageTag());
			if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {
				stmt.setString(i++, locale.toLanguageTag());
			}
		}
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
			for (AttributeDTO<?> attribute : criteria.getAttributes()) {
				stmt.setInt(i++, attribute.getId());
				for (AttributeValueDTO<?> valueContainer : attribute.getValuesByHandlingMode()) {
					stmt.setObject(i++, valueContainer.getValue(), AttributeUtils.getTargetSqlTypeIdentifier(attribute));
				}
			}
			stmt.setInt(i++, criteria.getAttributes().size());
		}
		return i;
	}

	private Results<LocalizedProductDTO> loadResults(Connection conn, ResultSet rs, Locale locale, int startPos, int pageSize)
			throws SQLException, DataException {

		Results<LocalizedProductDTO> results = new Results<LocalizedProductDTO>();
		results.setResultCount(JDBCUtils.getRowCount(rs));

		if (results.getResultCount() > 0 && pageSize > 0 && startPos > 0 && startPos <= results.getResultCount()) {
			int count = 0;
			rs.absolute(startPos);
			do {
				results.getPage().add(loadNextLocalized(conn, rs, locale));
				count++;
			} while (count<pageSize && rs.next());	
		}

		return results;
	}

	private void loadProductLocale(Connection conn, ProductDTO p) 
			throws DataException {

		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			stmt = conn.prepareStatement(LOAD_PRODUCT_LOCALE_QUERY);
			stmt.setLong(JDBCUtils.ID_CLAUSE_PARAMETER_INDEX, p.getId());
			rs = stmt.executeQuery();

			while (rs.next()) {
				int i = 1;
				Locale locale = Locale.forLanguageTag(rs.getString(i++));
				p.getNameI18n().put(locale, rs.getString(i++));
				p.getDescriptionI18n().put(locale, rs.getString(i++));
			}
		} catch (SQLException sqle) {
			logger.error(sqle);
			throw new DataException(sqle);
		} finally {
			JDBCUtils.close(stmt, rs);
		}
	}

	private ProductDTO loadNext(Connection conn, ResultSet rs, Locale locale) 
			throws SQLException, DataException {

		ProductDTO p = new ProductDTO();
		int i = 1;

		p = new ProductDTO();
		p.setId(rs.getLong(i++));
		i+=2;
		p.setCategoryId(rs.getShort(i++));
		p.setCategory(rs.getString(i++));
		i++;
		p.setLaunchDate(rs.getDate(i++));
		p.setStock(rs.getInt(i++));
		p.setPurchasePrice(rs.getDouble(i++));
		p.setSalePrice(rs.getDouble(i++));
		p.setReplacementId(JDBCUtils.getNullableLong(rs, i++));
		p.setReplacementName(rs.getString(i++));
		p.setAttributes(attributeDAO.findByProduct(conn, p.getId(), locale));
		loadProductLocale(conn, p);
		return p;
	}

	private LocalizedProductDTO loadNextLocalized(Connection conn, ResultSet rs, Locale locale) 
			throws SQLException, DataException {

		LocalizedProductDTO p = new LocalizedProductDTO();
		int i = 1;

		p.setId(rs.getLong(i++));
		i++;
		p.setName(rs.getString(i++));
		p.setCategoryId(rs.getShort(i++));
		p.setCategory(rs.getString(i++));
		p.setDescription(rs.getString(i++));
		p.setLaunchDate(rs.getDate(i++));
		p.setStock(rs.getInt(i++));
		p.setPurchasePrice(rs.getDouble(i++));
		p.setSalePrice(rs.getDouble(i++));
		p.setReplacementId(JDBCUtils.getNullableLong(rs, i++));
		p.setReplacementName(rs.getString(i++));
		p.setAttributes(attributeDAO.findByProduct(conn, p.getId(), locale));
		return p;
	}

}
