package com.pinguela.yourpc.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.dao.impl.CustomerOrderDAOImpl;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.service.CustomerOrderService;
import com.pinguela.yourpc.util.HibernateUtils;

public class CustomerOrderServiceImpl implements CustomerOrderService {

    private static Logger logger = LogManager.getLogger(CustomerOrderServiceImpl.class);
    private CustomerOrderDAO customerOrderDAO = null;

    public CustomerOrderServiceImpl() {
        customerOrderDAO = new CustomerOrderDAOImpl();
    }

    @Override
    public Long create(CustomerOrder co) 
            throws ServiceException, DataException {

        co.setTotalPrice(calculateTotalPrice(co));

        Session session = null;
        Transaction transaction = null;
        Long id = null;
        boolean commit = false;

        try {
            session = HibernateUtils.openSession();
            transaction = session.beginTransaction();
            id = customerOrderDAO.create(session, co);
            commit = id != null;
            return id;
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session, transaction, commit);
        }
    }

    private double calculateTotalPrice(CustomerOrder co) {
        double totalPrice = 0.0d;
        for (OrderLine ol : co.getOrderLines()) {
            totalPrice += (ol.getSalePrice() * ol.getQuantity());
        }
        return totalPrice;
    }

    @Override
    public Boolean update(CustomerOrder po) 
            throws ServiceException, DataException {

        Session session = null;
        Transaction transaction = null;
        boolean commit = false;

        try {
            session = HibernateUtils.openSession();
            transaction = session.beginTransaction();
            return customerOrderDAO.update(session, po) && (commit = true);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session, transaction, commit);
        }
    }

    @Override
    public CustomerOrder findById(Long id) 
            throws ServiceException, DataException {

        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return customerOrderDAO.findById(session, id);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }

    @Override
    public List<CustomerOrder> findByCustomer(Integer customerId) 
            throws ServiceException, DataException {

        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return customerOrderDAO.findByCustomer(session, customerId);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }

    @Override
    public List<CustomerOrder> findBy(CustomerOrderCriteria criteria) 
            throws ServiceException, DataException {

        if (criteria == null) {
            throw new IllegalArgumentException("Required parameter is missing.");
        }

        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return customerOrderDAO.findBy(session, criteria);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }

    @Override
    public CustomerOrderRanges getRanges(CustomerOrderCriteria criteria) 
            throws ServiceException, DataException {

        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return customerOrderDAO.getRanges(session, criteria);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
}
