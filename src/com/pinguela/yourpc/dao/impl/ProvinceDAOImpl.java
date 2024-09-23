package com.pinguela.yourpc.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.ProvinceDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.Country;
import com.pinguela.yourpc.model.Province;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class ProvinceDAOImpl 
extends AbstractDAO<Province>
implements ProvinceDAO {

	private static Logger logger = LogManager.getLogger(ProvinceDAOImpl.class);

	public ProvinceDAOImpl() {
		super(Province.class);
	}

	@Override
	public List<Province> findByCountry(Session session, String countryId)
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Province> query = builder.createQuery(getTargetClass());
			Root<Province> root = query.from(getTargetClass());

			Join<Province, Country> joinCountry = root.join("country");
			query.where(builder.equal(joinCountry.get("id"), countryId));
			query.orderBy(builder.asc(root.get("name")));

			return session.createQuery(query).getResultList();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	protected void setFindByCriteria(CriteriaBuilder builder, 
			CriteriaQuery<Province> query, Root<Province> root, AbstractCriteria<Province> criteria) {}

}
