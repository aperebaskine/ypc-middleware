package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.dto.AttributeDTO;

public class AttributeNameComparator implements Comparator<AttributeDTO<?>>{

	@Override
	public int compare(AttributeDTO<?> o1, AttributeDTO<?> o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
