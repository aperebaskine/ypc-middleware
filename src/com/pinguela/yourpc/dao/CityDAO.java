package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.dto.CityDTO;

public interface CityDAO {
	
	public List<CityDTO> findByProvince(Session session, Integer provinceId)
			throws DataException;

}
