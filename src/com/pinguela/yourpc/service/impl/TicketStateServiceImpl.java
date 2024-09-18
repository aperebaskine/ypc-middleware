package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketStateDAO;
import com.pinguela.yourpc.dao.impl.TicketStateDAOImpl;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketStateService;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketStateServiceImpl implements TicketStateService {
	
	private static Logger logger = LogManager.getLogger(TicketStateServiceImpl.class);
	
	private TicketStateDAO ticketStateDAO;
	
	public TicketStateServiceImpl() {
		ticketStateDAO = new TicketStateDAOImpl();
	}

	@Override
	public Map<String, EntityState<Ticket>> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return ticketStateDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
