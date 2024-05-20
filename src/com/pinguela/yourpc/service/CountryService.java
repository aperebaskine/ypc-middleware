package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Country;

public interface CountryService {
	
	public List<Country> findAll()
			throws ServiceException, DataException;


}
