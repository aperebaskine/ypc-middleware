package com.pinguela.yourpc.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractValueObject
implements ValueObject, Serializable {
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
