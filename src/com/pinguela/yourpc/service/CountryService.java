package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.dto.CountryDTO;

public interface CountryService {
	
	public List<CountryDTO> findAll()
			throws ServiceException, DataException;


}
