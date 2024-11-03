package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CategoryDAO;
import com.pinguela.yourpc.dao.impl.CategoryDAOImpl;
import com.pinguela.yourpc.model.Category;
import com.pinguela.yourpc.service.CategoryService;
import com.pinguela.yourpc.util.HibernateUtils;

public class CategoryServiceImpl implements CategoryService {
    
    private static Logger logger = LogManager.getLogger(CategoryServiceImpl.class);
    private CategoryDAO categoryDAO = null;

    public CategoryServiceImpl() {
        categoryDAO = new CategoryDAOImpl();
    }

    @Override
    public Map<Short, Category> findAll() 
            throws ServiceException, DataException {
        
        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return categoryDAO.findAll(session);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
}
