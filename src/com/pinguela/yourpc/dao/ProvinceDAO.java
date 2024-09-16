package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.Province;

public interface ProvinceDAO {
	
	public List<Province> findByCountry(Session session, String countryId)
			throws DataException;

}
