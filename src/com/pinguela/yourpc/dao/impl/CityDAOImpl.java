package com.pinguela.yourpc.dao.impl;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CityDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.model.SimpleCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CityDAOImpl 
extends AbstractDAO<Integer, City>
implements CityDAO {

	private static Logger logger = LogManager.getLogger(CityDAOImpl.class);

	public CityDAOImpl() {
	}

	@Override
	public List<City> findByProvince(Session session, Integer provinceId)
			throws DataException {
		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<City> query = builder.createQuery(getTargetClass());
			Root<City> root = query.from(getTargetClass());
			
			query.where(builder.equal(root.get("province").get("id"), provinceId));
			query.orderBy(builder.asc(root.get("name")));
			return session.createQuery(query).getResultList();
		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, 
			Root<City> root, AbstractCriteria<City> criteria) {
		SimpleCriteria<City> cityCriteria = (SimpleCriteria<City>) criteria; 
		return Arrays.asList(builder.equal(root.get("province").get("id"), cityCriteria.getValue()));
	}
	
	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<City> query, Root<City> root,
			AbstractCriteria<City> criteria) {
		// Unused	
	}

}
