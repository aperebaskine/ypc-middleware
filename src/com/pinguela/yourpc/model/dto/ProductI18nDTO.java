package com.pinguela.yourpc.model.dto;

import java.util.Locale;
import java.util.Map;

public class ProductI18nDTO 
extends AbstractProductDTO {

	private Map<Locale, String> nameI18n;
	private Map<Locale, String> descriptionI18n;

	public Map<Locale, String> getNameI18n() {
		return nameI18n;
	}
	public void setNameI18n(Map<Locale, String> nameI18n) {
		this.nameI18n = nameI18n;
	}
	public Map<Locale, String> getDescriptionI18n() {
		return descriptionI18n;
	}
	public void setDescriptionI18n(Map<Locale, String> descriptionI18n) {
		this.descriptionI18n = descriptionI18n;
	}

}
