package com.pinguela.yourpc.model;

public interface Criteria<PK, T> {

	Boolean ASC = Boolean.TRUE;
	Boolean DESC = Boolean.FALSE;
	
	PK getId();
	
	void setId(PK id);

	String getOrderBy();

	void setOrderBy(String orderBy);

	Boolean getAscDesc();

	void setAscDesc(Boolean ascDesc);

}