package com.pinguela.yourpc.model;

import java.util.ArrayList;
import java.util.List;

public class Results<E> 
extends AbstractValueObject {

	private int resultCount = 0;
	private List<E> page = null;
	
	public Results() {
		page = new ArrayList<E>();
	}
	
	public static <E> Results<E> singleEntry(E entry) {
		Results<E> results = new Results<E>();
		if (entry != null) {
			results.getPage().add(entry);
			results.setResultCount(1);
		}
		return results;
	}
	
	public Integer getResultCount() {
		return resultCount;
	}
	
	public void setResultCount(Integer resultCount) {
		this.resultCount = resultCount;
	}
	
	public List<E> getPage() {
		return page;
	}
	
	public void setPage(List<E> results) {
		this.page = results;
	}
	
}
