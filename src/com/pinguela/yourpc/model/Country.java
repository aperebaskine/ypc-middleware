package com.pinguela.yourpc.model;

public class Country 
extends AbstractTerritory<String> {

	public Country() {
		super();
	}

	public Country(String id, String name) {
		super(id, name);
	}

	public Country(String id) {
		super(id);
	}
	
}