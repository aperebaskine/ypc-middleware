package com.pinguela.yourpc.model;

import java.util.Locale;

public class EntityCriteria<PK, T> 
extends AbstractEntityCriteria<PK, T> {
	
	public EntityCriteria(PK id) {
		this(id, null);
	}
	
	public EntityCriteria(Locale locale) {
		this(null, locale);
	}
	
	public EntityCriteria(PK id, Locale locale) {
		setId(id);
		setLocale(locale);
	}
	
}
