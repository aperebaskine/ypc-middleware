package com.pinguela.yourpc.model.dto;

import com.pinguela.yourpc.model.AbstractEntity;
import com.pinguela.yourpc.model.AbstractValueObject;

public abstract class AbstractDTO<PK extends Comparable<PK>, T extends AbstractEntity<PK>>
extends AbstractValueObject {
	
	private PK id;
	
	protected AbstractDTO() {
	}
	
	protected AbstractDTO(PK id) {
		this.id = id;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

}
