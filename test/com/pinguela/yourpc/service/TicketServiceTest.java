package com.pinguela.yourpc.service;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.model.TicketType;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;
import com.pinguela.yourpc.util.DateUtils;

public class TicketServiceTest {

	private static Logger logger = LogManager.getLogger(TicketServiceTest.class);
	private TicketService ticketService = null;

	public TicketServiceTest() {
		ticketService = new TicketServiceImpl();
	}

	private void testFindById() 
			throws ServiceException, DataException {	
		
		Ticket t;

		t = ticketService.findById(2l);
		if (t == null) {
			logger.error("Ticket con ID {} no encontrado.", 2);
		} else {
			logger.debug("Ticket con ID {} encontrado: {}", 2, t);
		}
		
		t = ticketService.findById(-1l);
		if (t != null) {
			logger.error("Se esperaba un ticket nulo, recibido: {}", t);
		} else {
			logger.debug("Ticket con ID -1 nulo.");
		}
		
		t = ticketService.findById(null);
		if (t != null) {
			logger.error("Se esperaba un ticket nulo, recibido: {}", t);
		} else {
			logger.debug("Ticket con ID nulo nulo.");
		}
	}

	private void testFindBy()
			throws ServiceException, DataException {

		TicketCriteria criteria;
		Results<Ticket> results;
		
		criteria = new TicketCriteria();
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);
		
		criteria = new TicketCriteria();
		criteria.setCustomerId(4);
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setCustomerEmail("cant@see.me");
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setMinDate(DateUtils.getDate(2023, Calendar.NOVEMBER, 1));
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setMaxDate(DateUtils.getDate(2023, Calendar.OCTOBER, 1));
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setState("OPN");
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setType("PRO");
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);

		criteria = new TicketCriteria();
		criteria.setState("CLO");
		criteria.setType("RMA");
		criteria.setMinDate(DateUtils.getDate(2023, Calendar.AUGUST, 9));
		results = ticketService.findBy(criteria, 1, 10);
		logFindBy(results, criteria);
	}

	private static void logFindBy(Results<Ticket> results, TicketCriteria criteria) {
		
		logger.trace("Ejecutando findBy con criteria: {}", criteria);
		if (results == null || results.getPage().isEmpty()) {
			logger.error("Se esperaban resultados, recibido objeto nulo. Criteria: {}", criteria);
		} else {
			logger.debug("Tickets encontrados: {}", results);
		}
	}

	private void testCreate() 
			throws ServiceException, DataException {
		
		Ticket t = new Ticket();
		Customer c = new Customer();
		c.setId(7);
		t.setCustomer(c);
		
		TicketType type = new TicketType();
		type.setId("RMA");
		
		t.setType("RMA");
		t.setState("OPN");
		t.setTitle("TEST" +System.currentTimeMillis());
		t.setDescription("TEST" +System.currentTimeMillis());
		
		OrderLine ol = new OrderLine();
		ol.setId(35l);
		ol.setQuantity((short) 1);
		t.getOrderLines().add(ol);

		TicketMessage tm = new TicketMessage();
		tm.setCustomerId(7);
		tm.setText("TESTCREATE" +System.currentTimeMillis());
		t.getMessageList().add(tm);

		Long id = ticketService.create(t);
		if (id == null || !t.getTitle().equals(ticketService.findById(t.getId()).getTitle())) {
			logger.error("Error al insertar ticket: {}", t);
		} else {
			logger.debug("Ticket insertado correctamente con ID {}.", t.getId());
		}
	}

	public static void main(String[] args) 
			throws ServiceException, DataException {

		TicketServiceTest test = new TicketServiceTest();

		logger.trace("Testing findById");
		test.testFindById();

		logger.trace("Testing findBy");
		test.testFindBy();

		logger.trace("Testing create");
		test.testCreate();
	}
	
}
