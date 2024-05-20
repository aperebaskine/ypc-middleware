package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.City;

public interface CityService {
	
	public List<City> findByProvince(Integer provinceId)
			throws ServiceException, DataException;


}
