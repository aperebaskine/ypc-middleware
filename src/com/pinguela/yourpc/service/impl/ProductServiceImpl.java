package com.pinguela.yourpc.service.impl;

import java.util.Locale;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.ProductDAO;
import com.pinguela.yourpc.dao.impl.ProductDAOImpl;
import com.pinguela.yourpc.model.ProductCriteria;
import com.pinguela.yourpc.model.ProductRanges;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.dto.FullProductDTO;
import com.pinguela.yourpc.model.dto.LocalizedProductDTO;
import com.pinguela.yourpc.service.ProductService;
import com.pinguela.yourpc.util.HibernateUtils;

public class ProductServiceImpl implements ProductService {

	private static Logger logger = LogManager.getLogger(ProductServiceImpl.class);
	private ProductDAO productDAO = null;

	public ProductServiceImpl() {
		productDAO = new ProductDAOImpl();
	}

	@Override

	public Long create(FullProductDTO dto)  
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		
		Long id = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			id = productDAO.create(session, dto);
			commit = id != null;
			return id;

		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}

	@Override

	public Boolean update(FullProductDTO dto)  
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			return productDAO.update(session, dto) && (commit = true);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}

	@Override
	public Boolean delete(Long productId)  
			throws ServiceException, DataException {

		Session session = null;
		Transaction transaction = null;
		boolean commit = false;

		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			
			return productDAO.delete(session, productId) && (commit = true);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}
	
	@Override
	public FullProductDTO findById(Long id) 
			throws ServiceException, DataException {
		
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return productDAO.findById(session, id);

		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public LocalizedProductDTO findById(Long id, Locale locale)  
			throws ServiceException, DataException {

		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return productDAO.findById(session, id, locale);

		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}

	@Override
	public Results<LocalizedProductDTO> findBy(ProductCriteria criteria, int startPos, int pageSize)  
			throws ServiceException, DataException {

		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return productDAO.findBy(session, criteria, startPos, pageSize);

		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}
	
	@Override
	public ProductRanges getRanges(ProductCriteria criteria) throws ServiceException, DataException {
		
		Session session = null;

		try {
			session = HibernateUtils.openSession();
			return productDAO.getRanges(session, criteria);

		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session);
		}
	}
}
