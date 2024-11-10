package com.pinguela.yourpc.dao;

import java.sql.Connection;
import java.util.List;
import java.util.Locale;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.Ticket;

public interface OrderLineDAO {
	
	public List<OrderLine> findByCustomerOrder(Connection conn, long orderId, Locale locale)
			throws DataException;
	
	public List<OrderLine> findByTicket(Connection conn, long ticketId, Locale locale)
			throws DataException;
	
	public List<OrderLine> findByRMA(Connection conn, long rmaId, Locale locale)
			throws DataException;
	
	public Boolean create(Connection conn, List<OrderLine> orderLines)
			throws DataException;
		
	public Boolean deleteByCustomerOrder(Connection conn, long orderId)
			throws DataException;
	
	public Boolean assignToTicket(Connection conn, Ticket t)
			throws DataException;

	public Boolean assignToRMA(Connection conn, RMA r)
			throws DataException;
	
}
