package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Province;

public interface ProvinceService {
	
	public List<Province> findByCountry(String countryId)
			throws ServiceException, DataException;


}
