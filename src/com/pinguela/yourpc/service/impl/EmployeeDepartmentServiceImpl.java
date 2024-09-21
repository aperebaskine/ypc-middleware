package com.pinguela.yourpc.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.EmployeeDepartmentDAO;
import com.pinguela.yourpc.dao.impl.EmployeeDepartmentDAOImpl;
import com.pinguela.yourpc.service.EmployeeDepartmentService;
import com.pinguela.yourpc.util.HibernateUtils;

public class EmployeeDepartmentServiceImpl implements EmployeeDepartmentService {
	
	private static Logger logger = LogManager.getLogger(EmployeeDepartmentServiceImpl.class);
	
	private EmployeeDepartmentDAO employeeDepartmentDAO = null;
	
	public EmployeeDepartmentServiceImpl() {
		employeeDepartmentDAO = new EmployeeDepartmentDAOImpl();
	}

	@Override
	public Integer assignToEmployee(Integer employeeId, String departmentId) throws ServiceException, DataException {
		
		Session session = null;
		Transaction transaction = null;
		boolean commit = false;
		
		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			Integer result = employeeDepartmentDAO.assignToEmployee(session, employeeId, departmentId);
			commit = true; 
			return result;
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}

	@Override
	public Boolean unassign(Integer employeeId) throws ServiceException, DataException {
		
		Session session = null;
		Transaction transaction = null;
		boolean commit = false;
		
		try {
			session = HibernateUtils.openSession();
			transaction = session.beginTransaction();
			return employeeDepartmentDAO.unassign(session, employeeId) && (commit = true);
			
		} catch (HibernateException e) {
			logger.fatal(e.getMessage(), e);
			throw new ServiceException(e);
		} finally {
			HibernateUtils.close(session, transaction, commit);
		}
	}
}
