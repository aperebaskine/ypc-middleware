package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;

public interface RMADAO {
	
	public RMA findById(Connection conn, Long rmaId, Locale locale)
			throws DataException;
	
	public List<RMA> findBy(Connection conn, RMACriteria criteria, Locale locale)
			throws DataException;
	
	public boolean matchesCustomer(Connection conn, Integer ticketId, Integer customerId)
			throws DataException;
	
	public Long create(Connection conn, RMA rma)
			throws DataException;
	
	public Boolean update(Connection conn, RMA rma)
			throws DataException;

}
