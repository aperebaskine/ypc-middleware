package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketTypeDAO;
import com.pinguela.yourpc.dao.impl.TicketTypeDAOImpl;
import com.pinguela.yourpc.model.ItemType;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketTypeService;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketTypeServiceImpl implements TicketTypeService {
	
	private static Logger logger = LogManager.getLogger(TicketTypeServiceImpl.class);
	
	private TicketTypeDAO ticketTypeDAO;
	
	public TicketTypeServiceImpl() {
		ticketTypeDAO = new TicketTypeDAOImpl();
	}

	@Override
	public Map<String, ItemType<Ticket>> findAll() throws ServiceException, DataException {
		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return ticketTypeDAO.findAll(conn);
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn);
		}
	}

}
