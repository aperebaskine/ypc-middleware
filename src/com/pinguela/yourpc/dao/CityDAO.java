package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.City;

public interface CityDAO {
	
	public List<City> findByProvince(Session session, Integer provinceId)
			throws DataException;

}
