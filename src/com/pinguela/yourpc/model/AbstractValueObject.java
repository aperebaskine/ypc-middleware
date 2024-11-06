package com.pinguela.yourpc.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

@SuppressWarnings("serial")
public abstract class AbstractValueObject implements Serializable {
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
