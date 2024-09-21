package com.pinguela.yourpc.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import com.pinguela.yourpc.model.AbstractValueObject;

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
		Reflections reflections = new Reflections(AbstractValueObject.class.getPackage().getName());
		
		for (Class<?> clazz : reflections.getSubTypesOf(AbstractValueObject.class)) {
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

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
