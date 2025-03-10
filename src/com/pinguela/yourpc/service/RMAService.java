package com.pinguela.yourpc.service;

import java.util.List;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;

public interface RMAService {

	public RMA findById(Long rmaId, Locale locale)
			throws ServiceException, DataException;
	
	public List<RMA> findBy(RMACriteria criteria, Locale locale)
			throws ServiceException, DataException;
	
	public boolean matchesCustomer(Integer ticketId, Integer customerId)
			throws ServiceException, DataException;
	
	public Long create(RMA rma)
			throws ServiceException, DataException;
	
	public Boolean update(RMA rma)
			throws ServiceException, DataException;
}
