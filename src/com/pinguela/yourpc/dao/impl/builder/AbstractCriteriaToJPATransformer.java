package com.pinguela.yourpc.dao.impl.builder;

import java.util.ArrayList;
import java.util.List;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractEntityCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.metamodel.ListAttribute;

public abstract class AbstractCriteriaToJPATransformer<E extends AbstractEntity<?>, C extends AbstractEntityCriteria<?, E>> {
	
	private ListAttribute<E, ?> localeJoin;
	
	protected AbstractCriteriaToJPATransformer() {
		this(null);
	}
	
	protected AbstractCriteriaToJPATransformer(ListAttribute<E, ?> localeJoin) {
		this.localeJoin = localeJoin;
	}

	@SuppressWarnings("unchecked")
	public List<Predicate> transform(CriteriaBuilder cb, Root<E> root, AbstractEntityCriteria<?, E> criteria) {
		List<Predicate> predicates = new ArrayList<Predicate>();
		
		if (localeJoin != null && criteria.getLocale() != null) {
			predicates.add(cb.equal(root.join(localeJoin).get("id"), criteria.getLocale().toLanguageTag()));
		}
		
		if (criteria.getId() != null) {
			predicates.add(cb.equal(root.get("id"), criteria.getId()));
			return predicates;
		} else {
			return doTransform(cb, root, null, (C) criteria);
		}
	}
	
	protected abstract List<Predicate> doTransform(CriteriaBuilder cb, Root<E> root, List<Predicate> commonPredicates, C criteria);
	
}
