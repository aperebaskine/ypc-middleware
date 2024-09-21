package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;

public interface RMADAO {
	
	public RMA findById(Session session, Long rmaId)
			throws DataException;
	
	public List<RMA> findBy(Session session, RMACriteria criteria)
			throws DataException;
	
	public Long create(Session session, RMA rma)
			throws DataException;
	
	public Boolean update(Session session, RMA rma)
			throws DataException;

}
