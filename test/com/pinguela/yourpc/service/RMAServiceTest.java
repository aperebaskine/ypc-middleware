package com.pinguela.yourpc.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.OrderLine;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.model.RMAState;
import com.pinguela.yourpc.service.impl.RMAServiceImpl;
import com.pinguela.yourpc.util.DateUtils;

public class RMAServiceTest {
	
	private static Logger logger = LogManager.getLogger(RMAServiceTest.class);
	private RMAService rmaService = null;
	private RMA testRma = null;
	
	public RMAServiceTest() {
		rmaService = new RMAServiceImpl();
		
		testRma = new RMA();
		OrderLine ol = new OrderLine();
		
		Customer c = new Customer();
		c.setId(3);
		testRma.setCustomer(c);
		
		RMAState state = new RMAState();
		state.setId("PRS");
		testRma.setState(state);
		
		testRma.setTrackingNumber("TEST" +System.currentTimeMillis());
		
		ol.setId(35l);
		ol.setQuantity((short) 1);
		testRma.getOrderLines().add(ol);
	}
	
	private void testFindById() 
			throws Exception {
		
		RMA r;

		r = rmaService.findById(2l);
		if (r == null) {
			logger.error("Devolución no encontrada.");
		} else {
			logger.debug("Devolución con ID 2 encontrado: {}", r);
		}
		
		r = rmaService.findById(-1l);
		if (r != null) {
			logger.error("Se esperaba nulo, recibido: {}", r);
		} else {
			logger.debug("Devolución nula.");
		}
		
		r = rmaService.findById(null);
		if (r != null) {
			logger.error("Se esperaba nulo, recibido: {}", r);
		} else {
			logger.debug("Devolución nula.");
		}
	}
	
	private void testFindByEmptyCriteria()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByCustomerId()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setCustomerId(5);
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByCustomerEmail()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setCustomerEmail("cant@see.me");
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByOrderId()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setOrderId(8l);
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByTicketId()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setTicketId(4l);
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByMinDate()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setMinDate(DateUtils.getDate(2023, Calendar.AUGUST, 15));
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByMaxDate()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setMaxDate(DateUtils.getDate(2023, Calendar.AUGUST, 15));
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByState()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setState("APP");
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testFindByMultipleCriteria()
			throws Exception {
		
		RMACriteria criteria = new RMACriteria();
		criteria.setState("APP");
		criteria.setMaxDate(DateUtils.getDate(2023, Calendar.AUGUST, 15));
		criteria.setCustomerId(5);
		List<RMA> results = new ArrayList<RMA>();
		
		results = rmaService.findBy(criteria);
		if (results == null || results.isEmpty()) {
			logger.error("Resultados no encontrados.");
		} else {
			logger.debug("Resultados encontrados: {}", results);
		}
	}
	
	private void testCreate() 
			throws Exception {
		
		Long id = rmaService.create(testRma);
		if (id == null
				|| !testRma.getTrackingNumber().equals(rmaService.findById(testRma.getId()).getTrackingNumber())) {
			logger.error("Error al crear devolucion {}", testRma);
		} else {
			logger.debug("Devolución creada correctamente.");
		}
	}
	
	private void testUpdate() 
			throws Exception {
		
		testRma.setTrackingNumber("TESTUPDATE" +System.currentTimeMillis());
		
		if (!rmaService.update(testRma)
				|| !testRma.getTrackingNumber().equals(rmaService.findById(testRma.getId()).getTrackingNumber())) {
			logger.error("Error al actualizar {}", testRma);
		} else {
			logger.debug("Devolución actualizada correctamente.");
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		
		RMAServiceTest test = new RMAServiceTest();
		
		logger.trace("Testing findById");
		test.testFindById();
		
		logger.trace("Testing findByEmptyCriteria");
		test.testFindByEmptyCriteria();
		
		logger.trace("Testing findByCustomerId");
		test.testFindByCustomerId();
		
		logger.trace("Testing findByCustomerEmail");
		test.testFindByCustomerEmail();
		
		logger.trace("Testing findByOrderId");
		test.testFindByOrderId();
		
		logger.trace("Testing findByTicketId");
		test.testFindByTicketId();
		
		logger.trace("Testing findByMinDate");
		test.testFindByMinDate();
		
		logger.trace("Testing findByMaxDate");
		test.testFindByMaxDate();
		
		logger.trace("Testing findByState");
		test.testFindByState();
		
		logger.trace("Testing findByMultipleCriteria");
		test.testFindByMultipleCriteria();
		
		logger.trace("Testing create");
		test.testCreate();
		
		logger.trace("Testing update");
		test.testUpdate();
	}

}
