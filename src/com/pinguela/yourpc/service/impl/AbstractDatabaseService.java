package com.pinguela.yourpc.service.impl;

import org.hibernate.SessionFactory;

import com.pinguela.yourpc.util.HibernateUtils;

public abstract class AbstractDatabaseService {
	
	protected SessionFactory sessionFactory = HibernateUtils.getSessionFactory();

}
