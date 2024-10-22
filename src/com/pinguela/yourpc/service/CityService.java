package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.dto.CityDTO;

public interface CityService {
	
	public List<CityDTO> findByProvince(Integer provinceId)
			throws ServiceException, DataException;


}
