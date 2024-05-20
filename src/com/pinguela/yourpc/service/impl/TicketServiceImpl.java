package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketDAO;
import com.pinguela.yourpc.dao.impl.TicketDAOImpl;
import com.pinguela.yourpc.model.Results;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.model.TicketCriteria;
import com.pinguela.yourpc.service.TicketService;
import com.pinguela.yourpc.util.JDBCUtils;

public class TicketServiceImpl 
implements TicketService {

	private static Logger logger = LogManager.getLogger(TicketServiceImpl.class);
	private TicketDAO ticketDAO = null;

	public TicketServiceImpl() {
		ticketDAO = new TicketDAOImpl();
	}

	@Override
	public Ticket findById(Long ticketId) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return ticketDAO.findById(conn, ticketId);
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Results<Ticket> findBy(TicketCriteria criteria, int pos, int pageSize) 
			throws ServiceException, DataException {

		Connection conn = null;

		try {
			conn = JDBCUtils.getConnection();
			return ticketDAO.findBy(conn, criteria, pos, pageSize);
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Long create(Ticket ticket)
			throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			Long id = ticketDAO.create(conn, ticket);
			commit = id != null;
			return id;
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

	@Override
	public Boolean update(Ticket ticket) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (ticketDAO.update(conn, ticket)) {
				commit = true;
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}

}
