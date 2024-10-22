package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CountryDAO;
import com.pinguela.yourpc.dao.impl.CountryDAOImpl;
import com.pinguela.yourpc.model.dto.CountryDTO;
import com.pinguela.yourpc.service.CountryService;
import com.pinguela.yourpc.util.HibernateUtils;


public class CountryServiceImpl 
implements CountryService {
	
	private static Logger logger = LogManager.getLogger(CountryServiceImpl.class);
	private CountryDAO countryDAO = null;
	
	public CountryServiceImpl() {
		countryDAO = new CountryDAOImpl();
	}

	@Override
	public List<CountryDTO> findAll() 
			throws ServiceException, DataException {
		
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return countryDAO.findAll(session);
		} catch (Exception e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

}
