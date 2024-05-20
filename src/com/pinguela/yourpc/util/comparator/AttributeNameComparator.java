package com.pinguela.yourpc.util.comparator;

import java.util.Comparator;

import com.pinguela.yourpc.model.Attribute;

public class AttributeNameComparator implements Comparator<Attribute<?>>{

	@Override
	public int compare(Attribute<?> o1, Attribute<?> o2) {
		return o1.getName().compareTo(o2.getName());
	}

}
