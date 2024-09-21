package com.pinguela.yourpc.dao;

import java.util.List;

import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.Ticket;

public interface OrderLineDAO {
	
	public List<OrderLine> findByCustomerOrder(Session session, long orderId)
			throws DataException;
	
	public List<OrderLine> findByTicket(Session session, long ticketId)
			throws DataException;
	
	public List<OrderLine> findByRMA(Session session, long rmaId)
			throws DataException;
	
	public Boolean create(Session session, List<OrderLine> orderLines)
			throws DataException;
		
	public Boolean deleteByCustomerOrder(Session session, long orderId)
			throws DataException;
	
	public Boolean assignToTicket(Session session, Ticket t)
			throws DataException;

	public Boolean assignToRMA(Session session, RMA r)
			throws DataException;
	
}
