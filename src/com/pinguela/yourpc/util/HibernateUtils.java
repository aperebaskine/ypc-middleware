package com.pinguela.yourpc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import com.pinguela.yourpc.model.AbstractEntity;

import jakarta.persistence.Entity;

public class HibernateUtils {

	private static Logger logger = LogManager.getLogger(HibernateUtils.class);

	private static ServiceRegistry serviceRegistry;
	private static SessionFactory sessionFactory;

	static {
		try {
			serviceRegistry = initializeServiceRegistry();
			sessionFactory = initializeSessionFactory();
		} finally {
			scheduleCleanupOnExit();
		}
	}

	private static ServiceRegistry initializeServiceRegistry() {
		try {
			return new StandardServiceRegistryBuilder().build();
		} catch (Exception e) {
			logger.fatal(String.format("Failed to initialize ServiceRegistry. Exception thrown: %s", e.getMessage()), e);
			throw new IllegalStateException(e);
		} 
	}

	private static SessionFactory initializeSessionFactory() {
		try {
			Metadata metadata = getMetadata(serviceRegistry);	
			return metadata.buildSessionFactory();

		} catch (Exception e) {
			logger.fatal(String.format("Failed to initialize SessionFactory. Exception thrown: %s", e.getMessage()), e);
			throw new IllegalStateException(e);
		} 
	}

	private static Metadata getMetadata(ServiceRegistry serviceRegistry) {
		MetadataSources sources = new MetadataSources(serviceRegistry);
		Reflections reflections = new Reflections(AbstractEntity.class.getPackage().getName());

		for (Class<?> clazz : reflections.getSubTypesOf(AbstractEntity.class)) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				sources.addAnnotatedClass(clazz);
			}
		}

		MetadataBuilder builder = sources.getMetadataBuilder();
		return builder.build();
	}

	private static void scheduleCleanupOnExit() {
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			closeResources();
		}));
	}

	private static void closeResources() {
		if (sessionFactory != null) {
			sessionFactory.close();
		}	
		if (serviceRegistry != null) {
			serviceRegistry.close();
		}
	}

	public static Session openSession() {		
		try {
			Session session = sessionFactory.openSession();
			logger.info("Hibernate session opened.");
			return session;
		} catch (HibernateException e) {
			logger.error(String.format("Unable to open Hibernate session, exception thrown: %s", e.getMessage()), e);
			throw e;
		}
	}

	public static void close(Session session) {

		if (session == null) {
			return;
		}

		try {
			session.close();
			logger.info("Session successfully closed.");
		} catch (HibernateException e) {
			logger.warn(String.format(
					"Exception thrown while attempting to close session: %s", e.getMessage()), e);
		}
	}

	public static void close(Transaction transaction, boolean commit) {

		if (transaction == null || !transaction.isActive()) {
			return;
		}

		try {
			if (!commit) {
				transaction.rollback();
				logger.info("Transaction rolled back.");
			} else {
				transaction.commit();
				logger.info("Transaction successfully commited.");
			} 
		} catch (HibernateException e) {
			logger.error(
					String.format("Exception thrown while attempting to %s transaction: %s",
							commit ? "commit" : "roll back", e.getMessage()), e);
			throw e;
		}
	}

	public static void close(Session session, Transaction transaction, boolean commit) {
		close(transaction, commit);
		close(session);
	}

}
