package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;

public interface RMADAO {
	
	public RMA findById(Connection conn, Long rmaId)
			throws DataException;
	
	public List<RMA> findBy(Connection conn, RMACriteria criteria)
			throws DataException;
	
	public Long create(Connection conn, RMA rma)
			throws DataException;
	
	public Boolean update(Connection conn, RMA rma)
			throws DataException;

}
