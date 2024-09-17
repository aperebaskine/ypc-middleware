package com.pinguela.yourpc.service.impl;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataBuilder;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import com.pinguela.yourpc.model.AbstractValueObject;

import jakarta.persistence.Entity;

public abstract class AbstractDatabaseService {
	
	private static final String MODEL_PACKAGE_NAME = "com.pinguela.yourpc.model";
	
	protected static final SessionFactory SESSION_FACTORY;
	
	static {
		ServiceRegistry standardRegistry =
		        new StandardServiceRegistryBuilder().build();

		MetadataSources sources = new MetadataSources(standardRegistry);
		
		Reflections reflections = new Reflections(MODEL_PACKAGE_NAME);
		for (Class<?> clazz : reflections.getSubTypesOf(AbstractValueObject.class)) {
			if (clazz.isAnnotationPresent(Entity.class)) {
				sources.addAnnotatedClass(clazz);
			}
		}
		
		MetadataBuilder builder = sources.getMetadataBuilder();
		builder.applyPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy());
		
		Metadata metadata = builder.build();
		
		SESSION_FACTORY = metadata.buildSessionFactory();
	}

}
