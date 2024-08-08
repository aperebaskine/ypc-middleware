package com.pinguela.yourpc.model;

public interface Criteria<T> {

	Boolean ASC = Boolean.TRUE;
	Boolean DESC = Boolean.FALSE;

	String getOrderBy();

	void setOrderBy(String orderBy);

	Boolean getAscDesc();

	void setAscDesc(Boolean ascDesc);
	
}
