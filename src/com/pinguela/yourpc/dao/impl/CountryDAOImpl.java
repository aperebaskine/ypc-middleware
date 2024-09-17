package com.pinguela.yourpc.dao.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CountryDAO;
import com.pinguela.yourpc.model.Country;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;

public class CountryDAOImpl implements CountryDAO {
	
	private static final Logger logger = LogManager.getLogger(CountryDAOImpl.class);
	
	@Override
	public List<Country> findAll(Session session) 
		throws DataException {
		
	    try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Country> criteria = builder.createQuery(Country.class);
			criteria.from(Country.class);
			List<Country> data = session.createQuery(criteria).getResultList();
			return data;
		} catch (Exception e) {
			logger.error(e);
			throw new DataException(e);
		}
	}

}
