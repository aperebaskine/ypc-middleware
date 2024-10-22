package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.dto.CountryDTO;

public interface CountryDAO {
	
	public List<CountryDTO> findAll(Session session)
			throws DataException;

}
