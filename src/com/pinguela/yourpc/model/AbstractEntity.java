package com.pinguela.yourpc.model;

public abstract class AbstractEntity<PK> 
extends AbstractValueObject {
	
	public abstract PK getId();

}
