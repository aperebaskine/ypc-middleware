package com.pinguela.yourpc.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.AttributeDAO;
import com.pinguela.yourpc.dao.ProductDAO;
import com.pinguela.yourpc.dao.transformer.ProductTransformer;
import com.pinguela.yourpc.dao.util.AttributeUtils;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Attribute;
import com.pinguela.yourpc.model.AttributeValue;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.model.Product;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.util.CategoryUtils;
import com.pinguela.yourpc.util.SQLQueryUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProductDAOImpl 
extends AbstractMutableDAO<Long, Product>
implements ProductDAO {

	private static final Map<String, String> PRODUCT_COLUMN_ALIASES = 
			SQLQueryUtils.generateColumnAliases(Product.class, TableDefinition.PRODUCT_COLUMNS.keySet());

	private static final String SELECT_QUERY_PLACEHOLDER =
			" SELECT %1$s"
					+ " FROM PRODUCT %2$s"
					+ " LEFT JOIN PRODUCT %3$s"
					+ " ON %2$s.REPLACEMENT_ID = %3$s.ID"
					+ " LEFT JOIN (PRODUCT_ATTRIBUTE_VALUE pav"
					+ " INNER JOIN ATTRIBUTE_VALUE %4$s"
					+ " ON %4$s.ID = pav.ATTRIBUTE_VALUE_ID"
					+ "	INNER JOIN ATTRIBUTE_TYPE %5$s"
					+ " on %5$s.ID = %4$s.ATTRIBUTE_TYPE_ID)"
					+ " ON pav.PRODUCT_ID = %2$s.ID";

	private static final String RANGE_QUERY_COLUMNS =
			" MIN(%1$s.STOCK),"
					+ " MAX(%1$s.STOCK),"
					+ " MIN(%1$s.SALE_PRICE),"
					+ " MAX(%1$s.SALE_PRICE),"
					+ " MIN(%1$s.LAUNCH_DATE),"
					+ " MAX(%1$s.LAUNCH_DATE)";

	private static final String SOFT_DELETE_QUERY = 
			" UPDATE PRODUCT SET DISCONTINUATION_DATE = :date WHERE ID = :id";

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
					TableDefinition.PRODUCT_ALIAS, 
					TableDefinition.REPLACEMENT_ALIAS,
					TableDefinition.ATTRIBUTE_VALUE_ALIAS,
					TableDefinition.ATTRIBUTE_ALIAS
					);

	private static final String BASE_COUNT_SUBQUERY = 
			String.format(
					SELECT_QUERY_PLACEHOLDER, 
					String.format(" COUNT(%1$s.ID)", TableDefinition.PRODUCT_SUBQUERY_ALIAS), 
					TableDefinition.PRODUCT_SUBQUERY_ALIAS, 
					TableDefinition.REPLACEMENT_ALIAS,
					TableDefinition.ATTRIBUTE_VALUE_ALIAS,
					TableDefinition.ATTRIBUTE_ALIAS
					);

	private static final String RANGES_QUERY =
			String.format(
					SELECT_QUERY_PLACEHOLDER, 
					String.format(RANGE_QUERY_COLUMNS, TableDefinition.PRODUCT_ALIAS), 
					TableDefinition.PRODUCT_ALIAS, 
					TableDefinition.REPLACEMENT_ALIAS,
					TableDefinition.ATTRIBUTE_VALUE_ALIAS,
					TableDefinition.ATTRIBUTE_ALIAS
					);

	private static Logger logger = LogManager.getLogger(ProductDAOImpl.class);
	private AttributeDAO attributeDAO;

	public ProductDAOImpl() {
		attributeDAO = new AttributeDAOImpl();
	}

	@Override
	public Long create(Session session, LocalizedProductDTO dto) 
			throws DataException {
		attributeDAO.saveAttributeValues(session, dto.getAttributes());
		return super.createEntity(session, toProduct(session, dto));
	}

	@Override
	public Boolean update(Session session, LocalizedProductDTO dto) 
			throws DataException {
		attributeDAO.saveAttributeValues(session, dto.getAttributes());
		return super.updateEntity(session, toProduct(session, dto));
	}

	@Override
	public Boolean delete(Session session, Long productId) 
			throws DataException {
		return session.createNativeQuery(SOFT_DELETE_QUERY, Product.class)
				.setParameter("date", new Date())
				.setParameter("id", productId)
				.executeUpdate() > 0;
	}

	@Override
	public LocalizedProductDTO findById(Session session, Long id) 
			throws DataException {

		if (id == null) {
			return null;
		}

		try {
			NativeQuery<LocalizedProductDTO> query = 
					session.createNativeQuery(BASE_QUERY + " WHERE p.ID = :id AND p.DISCONTINUATION_DATE IS NULL", LocalizedProductDTO.class);
			prepareQuery(query);
			return query.setParameter("id", id).getSingleResultOrNull();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	public Results<LocalizedProductDTO> findBy(Session session, ProductCriteria criteria, int pos, int pageSize) 
			throws DataException {

		StringBuilder queryStr = new StringBuilder(BASE_QUERY).append(buildQueryClauses(criteria));

		try {
			NativeQuery<LocalizedProductDTO> query = session.createNativeQuery(queryStr.toString(), LocalizedProductDTO.class);
			prepareQuery(query, pos, pageSize);
			setSelectValues(query, criteria);

			Results<LocalizedProductDTO> results = new Results<LocalizedProductDTO>();
			results.setResultCount(Long.valueOf(query.getResultCount()).intValue());
			results.setPage(query.getResultList());

			return results;

		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			throw new DataException(he);
		} 
	}

	@Override
	public ProductRanges getRanges(Session session, ProductCriteria criteria) 
			throws DataException {

		ProductRanges ranges = new ProductRanges();
		StringBuilder queryStr = new StringBuilder(RANGES_QUERY).append(buildQueryClauses(criteria, false));

		try {
			NativeQuery<Object[]> query = session.createNativeQuery(queryStr.toString(), Object[].class);  
			setSelectValues(query, criteria);

			Object[] results = query.getSingleResult();

			int i = 0;
			ranges.setStockMin((Integer) results[i++]);
			ranges.setStockMax((Integer) results[i++]);
			ranges.setPriceMin(((BigDecimal) results[i++]).doubleValue());
			ranges.setPriceMax(((BigDecimal) results[i++]).doubleValue());
			ranges.setLaunchDateMin((Date) results[i++]);
			ranges.setLaunchDateMax((Date) results[i++]);

			return ranges;
		} catch (HibernateException he) {
			logger.error(he.getMessage(), he);
			throw new DataException(he);
		}
	}
	
	private static StringBuilder buildQueryClauses(ProductCriteria criteria) {
		return buildQueryClauses(criteria, true);
	}

	private static StringBuilder buildQueryClauses(ProductCriteria criteria, boolean includeAttributes) {

		StringBuilder clauses = new StringBuilder();

		List<String> conditions = new ArrayList<String>();

		if (criteria.getName() != null) {
			conditions.add(" %1$s.NAME LIKE ?");
		}
		if (criteria.getLaunchDateMin() != null) {	
			conditions.add(" %1$s.LAUNCH_DATE >= ?");
		}
		if (criteria.getLaunchDateMax() != null) {	
			conditions.add(" %1$s.LAUNCH_DATE <= ?");
		}
		if (criteria.getStockMin() != null) {	
			conditions.add(" %1$s.STOCK >= ?");
		}
		if (criteria.getStockMax() != null) {	
			conditions.add(" %1$s.STOCK <= ?");
		}
		if (criteria.getPriceMin() != null) {
			conditions.add(" %1$s.SALE_PRICE >= ?");
		}
		if (criteria.getPriceMax() != null) {	
			conditions.add(" %1$s.SALE_PRICE <= ?");
		}
		if (criteria.getCategoryId() != null) {	
			String categoryCondition = 
					new StringBuilder(" %1$s.CATEGORY_ID")
					.append(SQLQueryUtils.buildPlaceholderComparisonClause(
							CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet())
							)
					.toString();
			conditions.add(categoryCondition);
		}
		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {	
			conditions.add(AttributeUtils.buildAttributeConditionClause(criteria.getAttributes()));
		}

		conditions.add(" %1$s.DISCONTINUATION_DATE IS NULL");

		clauses.append(SQLQueryUtils.buildWhereClause(applyTableAlias(conditions, TableDefinition.PRODUCT_ALIAS)));

		if (includeAttributes) {
			clauses.append(String.format(" GROUP BY %1$s.ID, %2$s.ID, %3$s.ID",
					TableDefinition.PRODUCT_ALIAS,
					TableDefinition.ATTRIBUTE_ALIAS,
					TableDefinition.ATTRIBUTE_VALUE_ALIAS));
		}

		if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {
			StringBuilder subselect = new StringBuilder(" HAVING (")
					.append(BASE_COUNT_SUBQUERY);

			conditions.add(0, String.format(" %1$s.ID = %2$s.ID", TableDefinition.PRODUCT_ALIAS, TableDefinition.PRODUCT_SUBQUERY_ALIAS));

			subselect.append(SQLQueryUtils.buildWhereClause(applyTableAlias(conditions, TableDefinition.PRODUCT_SUBQUERY_ALIAS)))
			.append(String.format(" GROUP BY %1$s.ID", TableDefinition.PRODUCT_SUBQUERY_ALIAS))
			.append(") = ?");

			clauses.append(subselect);
		}

		if (includeAttributes) {
			for (Entry<String, Boolean> entry : AttributeUtils.ATTRIBUTE_ORDER_BY_CLAUSES.entrySet()) {
				criteria.orderBy(entry.getKey(), entry.getValue());
			}
			clauses.append(SQLQueryUtils.buildOrderByClause(criteria, Product.class));
		}
		
		return clauses;
	}

	private static List<String> applyTableAlias(List<String> conditions, String tableAlias) {
		List<String> list = new ArrayList<String>();

		for (int i = 0; i < conditions.size(); i++) {
			String condition = conditions.get(i);
			list.add(String.format(condition, tableAlias));
		}

		return list;
	}

	private static int setSelectValues(NativeQuery<?> query, ProductCriteria criteria) {

		int loops = criteria.getAttributes().isEmpty() ? 1 : 2; // Loop twice if query has subselect
		int i = 1;

		for (int j = 0; j < loops; j++) {
			if (criteria.getName() != null) {
				query.setParameter(i++, SQLQueryUtils.wrapLike(criteria.getName()));
			}
			if (criteria.getLaunchDateMin() != null) {
				query.setParameter(i++, new java.sql.Date(criteria.getLaunchDateMin().getTime()));
			}
			if (criteria.getLaunchDateMax() != null) {
				query.setParameter(i++, new java.sql.Date(criteria.getLaunchDateMax().getTime()));
			}
			if (criteria.getStockMin() != null) {
				query.setParameter(i++, criteria.getStockMin());
			}
			if (criteria.getStockMax() != null) {
				query.setParameter(i++, criteria.getStockMax());
			}
			if (criteria.getPriceMin() != null) {
				query.setParameter(i++, criteria.getPriceMin());
			}
			if (criteria.getPriceMax() != null) {
				query.setParameter(i++, criteria.getPriceMax());
			}
			if (criteria.getCategoryId() != null) {
				for (Short categoryId : CategoryUtils.getLowerHierarchy(criteria.getCategoryId()).keySet()) {
					query.setParameter(i++, categoryId);
				}
			}
			if (criteria.getAttributes() != null && !criteria.getAttributes().isEmpty()) {
				for (Attribute<?> attribute : criteria.getAttributes().values()) {
					query.setParameter(i++, attribute.getName());
					for (AttributeValue<?> valueContainer : attribute.getValuesByHandlingMode()) {
						query.setParameter(i++, valueContainer.getValue());
					}
				}
				if (j == 1) {
					query.setParameter(i++, criteria.getAttributes().size());
				}
			} 
		}

		return i;
	}

	private static void prepareQuery(NativeQuery<LocalizedProductDTO> query) {
		prepareQuery(query, null, null);
	}

	private static void prepareQuery(NativeQuery<LocalizedProductDTO> query, Integer pos, Integer pageSize) {

		ProductTransformer transformer = new ProductTransformer();

		query.setTupleTransformer(transformer)
		.setResultListTransformer(transformer);

		for (String column : TableDefinition.PRODUCT_COLUMNS.keySet()) {
			query.addScalar(PRODUCT_COLUMN_ALIASES.get(column), TableDefinition.PRODUCT_COLUMNS.get(column));
		}
		for (String column : TableDefinition.ATTRIBUTE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_COLUMN_ALIASES.get(column), TableDefinition.ATTRIBUTE_COLUMNS.get(column));
		}
		for (String column : TableDefinition.ATTRIBUTE_VALUE_COLUMNS.keySet()) {
			query.addScalar(AttributeDAOImpl.ATTRIBUTE_VALUE_COLUMN_ALIASES.get(column), TableDefinition.ATTRIBUTE_VALUE_COLUMNS.get(column));
		}

		if (pos != null) {
			query.setFirstResult(pos -1);
		}
		if (pageSize != null) {
			query.setMaxResults(pageSize);
		}
	}

	private Product toProduct(Session session, LocalizedProductDTO dto) {
		Product p = new Product();
		p.setId(dto.getId());
		p.setName(dto.getName());
		p.setCategory(session.getReference(Category.class, dto.getCategoryId()));
		p.setDescription(dto.getDescription());
		p.setLaunchDate(dto.getLaunchDate());
		p.setDiscontinuationDate(dto.getDiscontinuationDate());
		p.setStock(dto.getStock());
		p.setPurchasePrice(dto.getPurchasePrice());
		p.setSalePrice(dto.getSalePrice());

		if (dto.getReplacementId() != null) {
			p.setReplacement(session.getReference(Product.class, dto.getReplacementId()));
		}

		p.setValues(groupValues(dto));

		return p;
	}

	private static List<AttributeValue<?>> groupValues(LocalizedProductDTO dto) {
		List<AttributeValue<?>> values = new ArrayList<AttributeValue<?>>();
		for (Attribute<?> attribute : dto.getAttributes().values()) {
			values.addAll(attribute.getValues());
		}
		return values;
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Product> root,
			AbstractCriteria<Product> criteria) {
		// Unused
		return null;
	}

	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Product> query, Root<Product> root,
			AbstractCriteria<Product> criteria) {
		// Unused	
	}

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<Product> updateQuery, Root<Product> root,
			AbstractUpdateValues<Product> updateValues) {
		// Unused
	}

}
