package com.pinguela.yourpc.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.service.impl.TicketMessageServiceImpl;
import com.pinguela.yourpc.service.impl.TicketServiceImpl;

public class TicketMessageServiceTest {

	private static Logger logger = LogManager.getLogger(TicketMessageServiceTest.class);
	private TicketService ticketService = null;
	private TicketMessageService tmService = null;
	private TicketMessage testMessage = null;

	public TicketMessageServiceTest() {
		ticketService = new TicketServiceImpl();
		tmService = new TicketMessageServiceImpl();

		testMessage = new TicketMessage();
		testMessage.setTicketId(1l);
		testMessage.setEmployeeId(4);
		testMessage.setText("TESTCREATE" +System.currentTimeMillis());
	}

	private void testCreate()
			throws ServiceException, DataException {


		
		Long id = tmService.create(testMessage);
		List<TicketMessage> ticketMessageList = ticketService.findById(testMessage.getTicketId()).getMessageList();
		
		if (id == null || !testMessage.getText().equals(ticketMessageList.get(ticketMessageList.size()-1).getText())) {
			logger.error("Error al crear mensaje: {}", testMessage);
		} else {
			logger.debug("Mensaje creado correctamente: {}", testMessage);
		}
	}

	private void testDelete() 
			throws ServiceException, DataException {
		
		Boolean isDeleted = tmService.delete(testMessage.getId());
		List<TicketMessage> ticketMessageList = ticketService.findById(testMessage.getTicketId()).getMessageList();
		
		if (!isDeleted
				|| ticketMessageList.get(ticketMessageList.size()-1).getId() == testMessage.getId()) {
			logger.error("Error al borrar el mensaje con ID {}.", testMessage.getId());
		} else {
			logger.debug("Mensaje con ID {} borrado correctamente.", testMessage.getId());
		}
	}

	public static void main(String[] args) 
			throws ServiceException, DataException {
		
		TicketMessageServiceTest test = new TicketMessageServiceTest();

		logger.trace("Testing create");
		test.testCreate();

		logger.trace("Testing delete");
		test.testDelete();
	}

}
