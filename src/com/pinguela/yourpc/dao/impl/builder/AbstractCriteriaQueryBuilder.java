package com.pinguela.yourpc.dao.impl.builder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;
import com.pinguela.yourpc.model.dto.AbstractDTO;
import com.pinguela.yourpc.util.StringUtils;
import com.pinguela.yourpc.util.XMLUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

public abstract class AbstractCriteriaQueryBuilder<PK extends Comparable<PK>, E extends AbstractEntity<PK>, D extends AbstractDTO<PK, E>, C extends AbstractEntityCriteria<PK, E>>
extends AbstractQueryBuilder<PK, E, D, C> {

	protected AbstractCriteriaQueryBuilder(Class<D> dtoClass, Class<E> entityClass) {
		super(dtoClass, entityClass); 
	}

	public Query<D> buildQuery(Session session, PK id, Locale locale, C criteria) {

		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<D> cq = builder.createQuery(getDtoClass());

		Root<E> root = cq.from(getEntityClass());

		if (criteria != null) {
			select(cq, builder, root, criteria);
			join(cq, builder, root, criteria);
			where(cq, builder, root, criteria);
			groupBy(cq, builder, root, criteria);
			cq.orderBy(buildOrderByClause(builder, root, criteria));
		}

		Query<D> query = session.createQuery(cq);
		setParameters(query, criteria);
		return query;
	}

	protected abstract void select(CriteriaQuery<D> query, CriteriaBuilder builder, Root<E> root, C criteria);
	protected abstract void join(CriteriaQuery<D> query, CriteriaBuilder builder, Root<E> root, C criteria);
	protected abstract void where(CriteriaQuery<D> query, CriteriaBuilder builder, Root<E> root, C criteria);
	protected abstract void groupBy(CriteriaQuery<D> query, CriteriaBuilder builder, Root<E> root, C criteria);
	protected abstract void having(CriteriaQuery<D> query, CriteriaBuilder builder, Root<E> root, C criteria);
	
	protected abstract void setParameters(Query<D> query, C criteria);

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

		String path = String.format("//mapping[@queryType='jpa' and ./entity='%1$s']/orderMapping[@default = 'true']", getEntityClass().getName());

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
				String.format("//mapping[queryType='jpa' and ./entity='%1$s']/orderMapping[./key='%2$s']/field", getEntityClass().getName(), key));
	}

	private static Path<?> buildPath(Root<?> root, String... pathComponents) {
		Path<?> path = root;
		for (String component : pathComponents) {
			path = path.get(component);
		}
		return path;
	}

}
