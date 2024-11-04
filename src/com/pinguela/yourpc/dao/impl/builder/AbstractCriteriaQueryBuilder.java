package com.pinguela.yourpc.dao.impl.builder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.StringUtils;
import com.pinguela.yourpc.util.XMLUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class AbstractCriteriaQueryBuilder<Q extends CriteriaQuery<D>, E extends AbstractEntity<?>, D extends AbstractDTO<E>, C extends AbstractCriteria<E>>
extends AbstractQueryBuilder<Q, E, D, C> {
	
	private Class<D> dtoClass;
	private Class<E> entityClass;
	
	protected AbstractCriteriaQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		this.dtoClass = dtoClass;
		this.entityClass = entityClass; 
	}
	
	private CriteriaQuery<D> buildQuery(Session session, C criteria) {
		
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<D> query = builder.createQuery(dtoClass);
		Root<E> root = query.from(entityClass);

		if (criteria != null) {
			Predicate[] where = buildWhereClause(builder, root, criteria);
			if (where.length > 0) {
				query.where(where);
			}

			List<Order> orderBy = buildOrderByClause(builder, root, criteria);
			if (!orderBy.isEmpty()) {
				query.orderBy(orderBy);
			}
		}

		return query;
	}

	protected final Predicate[] buildWhereClause(CriteriaBuilder builder,
			Root<E> root, C criteria) {
		List<Predicate> predicates = getCriteria(builder, root, criteria);
		return predicates.toArray(new Predicate[predicates.size()]);
	}

	private List<Order> buildOrderByClause(CriteriaBuilder builder, 
			Root<E> root, C criteria) {

		if (criteria.getOrderBy().isEmpty()) {
			criteria.getOrderBy().putAll(getDefaultOrder());
		}

		List<Order> orderBy = new LinkedList<>();
		for (String orderByKey : criteria.getOrderBy().keySet()) {

			String[] pathComponents = StringUtils.split(getOrder(orderByKey));
			Path<?> path = buildPath(root, pathComponents);

			orderBy.add(criteria.getOrderBy().get(orderByKey) == AbstractCriteria.ASC ?
					builder.asc(path) : builder.desc(path));
		}
		return orderBy;
	}

	private Map<String, Boolean> getDefaultOrder() {
		Map<String, Boolean> components = new LinkedHashMap<String, Boolean>();

		String path = String.format("//mapping[@queryType='jpa' and ./entity='%1$s']/orderMapping[@default = 'true']", entityClass.getName());

		NodeList nodeList = XMLUtils.getNodeList(XMLUtils.getXMLResource("sql_mapping.xml"), path);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			Node orderAttribute = node.getAttributes().getNamedItem("order");
			boolean ascDesc = orderAttribute == null ? AbstractCriteria.ASC :
				"asc".equals(orderAttribute.getNodeValue());

			components.put(XMLUtils.getTextNode(node, "./field"), ascDesc);
		}

		return components;	
	}

	private String getOrder(String key) {
		return XMLUtils.getTextNode(XMLUtils.getXMLResource("sql_mapping.xml"), 
				String.format("//mapping[queryType='jpa' and ./entity='%1$s']/orderMapping[./key='%2$s']/field", entityClass.getName(), key));
	}

	private Path<?> buildPath(Root<E> root, String... pathComponents) {
		Path<?> path = root;
		for (String component : pathComponents) {
			path = path.get(component);
		}
		return path;
	}
	
	protected void construct(CriteriaQuery<D> query, CriteriaBuilder builder) {
		// TODO builder.construct... This method will be abstract
	}
	
	/**
	 * Specify criteria for the queries performed by methods that create Query objects.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param query The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param criteria Object containing the criteria values
	 */
	protected abstract List<Predicate> getCriteria(CriteriaBuilder builder,
			Root<E> root, AbstractCriteria<E> criteria);

	/**
	 * Append the GROUP BY clause to the query performed by {@link #findBy(Session, AbstractCriteria)}
	 * or {@link #findBy(Session, AbstractCriteria, int, int)} based on the criteria provided.
	 * @param builder CriteriaBuilder object for building Predicates
	 * @param query The query created by the CriteriaBuilder
	 * @param root The query's root entity
	 * @param criteria Object containing the criteria values
	 */
	protected abstract void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<D> query,
			Root<E> root, C criteria);

}
