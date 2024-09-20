package com.pinguela.yourpc.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractValueObject
implements ValueObject {
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
