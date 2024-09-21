package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.DepartmentDAO;
import com.pinguela.yourpc.dao.impl.DepartmentDAOImpl;
import com.pinguela.yourpc.model.Department;
import com.pinguela.yourpc.service.DepartmentService;
import com.pinguela.yourpc.util.HibernateUtils;

public class DepartmentServiceImpl implements DepartmentService {

    private static Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);
    
    private DepartmentDAO departmentDAO;
    
    public DepartmentServiceImpl() {
        departmentDAO = new DepartmentDAOImpl();
    }

    @Override
    public Map<String, Department> findAll() throws ServiceException, DataException {
        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return departmentDAO.findAll(session);
        } catch (HibernateException e) {
            logger.fatal(e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
    
}
