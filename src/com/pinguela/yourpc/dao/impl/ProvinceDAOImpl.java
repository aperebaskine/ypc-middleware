package com.pinguela.yourpc.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.ProvinceDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Country_;
import com.pinguela.yourpc.model.Province;
import com.pinguela.yourpc.model.Province_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ProvinceDAOImpl 
extends AbstractDAO<Integer, Province>
implements ProvinceDAO {

	private static Logger logger = LogManager.getLogger(ProvinceDAOImpl.class);

	public ProvinceDAOImpl() {
	}

	@Override
	public List<Province> findByCountry(Session session, String countryId)
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Province> query = builder.createQuery(getTargetClass());
			Root<Province> root = query.from(getTargetClass());

			query.where(builder.equal(root.get(Province_.country).get(Country_.id), countryId));
			query.orderBy(builder.asc(root.get(Province_.name)));

			return session.createQuery(query).getResultList();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, Root<Province> root,
			AbstractCriteria<Province> criteria) {
		return null;
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<Province> query, Root<Province> root,
			AbstractCriteria<Province> criteria) {
		// Unused	
	}
	
}
