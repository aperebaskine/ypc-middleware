package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.dto.ProvinceDTO;

public interface ProvinceService {
	
	public List<ProvinceDTO> findByCountry(String countryId)
			throws ServiceException, DataException;


}
