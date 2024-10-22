package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.ProvinceDAO;
import com.pinguela.yourpc.dao.impl.ProvinceDAOImpl;
import com.pinguela.yourpc.model.dto.ProvinceDTO;
import com.pinguela.yourpc.service.ProvinceService;
import com.pinguela.yourpc.util.HibernateUtils;

public class ProvinceServiceImpl implements ProvinceService {
	
	private static Logger logger = LogManager.getLogger(ProvinceServiceImpl.class);
	private ProvinceDAO provinceDAO = null;
	
	public ProvinceServiceImpl() {
		provinceDAO = new ProvinceDAOImpl();
	}

	@Override
	public List<ProvinceDTO> findByCountry(String countryId) 
	        throws ServiceException, DataException {

	    Session session = null;

	    try {
	        session = HibernateUtils.openSession();
	        return provinceDAO.findByCountry(session, countryId);

	    } catch (HibernateException e) {
	        logger.fatal(e.getMessage(), e);
	        throw new ServiceException(e);
	    } finally {
	        HibernateUtils.close(session);
	    }
	}

}
