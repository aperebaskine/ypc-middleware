package com.pinguela.yourpc.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Embeddable
public class ID {

	@ManyToOne
	@JoinColumn(name = "DOCUMENT_TYPE_ID")
	private IDType type;
	
	private @Column(name = "DOCUMENT_NUMBER") String number;

	public IDType getType() {
		return type;
	}

	public void setType(IDType type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
}
