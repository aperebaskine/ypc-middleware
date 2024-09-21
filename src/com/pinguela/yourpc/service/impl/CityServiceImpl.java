package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CityDAO;
import com.pinguela.yourpc.dao.impl.CityDAOImpl;
import com.pinguela.yourpc.model.City;
import com.pinguela.yourpc.service.CityService;
import com.pinguela.yourpc.util.HibernateUtils;

public class CityServiceImpl implements CityService {

    private static Logger logger = LogManager.getLogger(CityServiceImpl.class);
    private CityDAO cityDAO = null;

    public CityServiceImpl() {
        cityDAO = new CityDAOImpl();
    }

    @Override
    public List<City> findByProvince(Integer provinceId) 
            throws ServiceException, DataException {

        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return cityDAO.findByProvince(session, provinceId);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
}
