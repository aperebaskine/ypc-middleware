package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketMessageDAO;
import com.pinguela.yourpc.dao.impl.TicketMessageDAOImpl;
import com.pinguela.yourpc.model.TicketMessage;
import com.pinguela.yourpc.service.TicketMessageService;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketMessageServiceImpl 
implements TicketMessageService {
	
	private static Logger logger = LogManager.getLogger(TicketMessageServiceImpl.class);
	private TicketMessageDAO ticketMessageDAO = null;
	
	public TicketMessageServiceImpl() {
		ticketMessageDAO = new TicketMessageDAOImpl();
	}

	@Override
	public Long create(TicketMessage ticketMessage) 
			throws ServiceException, DataException {
		
		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			Long id = ticketMessageDAO.create(conn, ticketMessage);
			commit = id != null;
			return id;
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean delete(Long messageId) 
			throws ServiceException, DataException {
		
		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			commit = ticketMessageDAO.delete(conn, messageId);
			return commit;
			
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

}
