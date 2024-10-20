package com.pinguela.yourpc.model;

public class OrderLineCriteria
extends AbstractEntityCriteria<Long, OrderLine> {
	
	private Long orderId;
	private Long ticketId;
	private Long rmaId;
	
	public OrderLineCriteria() {
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getTicketId() {
		return ticketId;
	}

	public void setTicketId(Long ticketId) {
		this.ticketId = ticketId;
	}

	public Long getRmaId() {
		return rmaId;
	}

	public void setRmaId(Long rmaId) {
		this.rmaId = rmaId;
	}

}
