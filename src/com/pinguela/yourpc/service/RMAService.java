package com.pinguela.yourpc.service;

import java.util.List;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;

public interface RMAService {

	public RMA findById(Long rmaId)
			throws ServiceException, DataException;
	
	public List<RMA> findBy(RMACriteria criteria)
			throws ServiceException, DataException;
	
	public Long create(RMA rma)
			throws ServiceException, DataException;
	
	public Boolean update(RMA rma)
			throws ServiceException, DataException;
}
