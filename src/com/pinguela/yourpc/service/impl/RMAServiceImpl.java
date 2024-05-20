package com.pinguela.yourpc.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.RMADAO;
import com.pinguela.yourpc.dao.impl.RMADAOImpl;
import com.pinguela.yourpc.model.RMA;
import com.pinguela.yourpc.model.RMACriteria;
import com.pinguela.yourpc.service.RMAService;
import com.pinguela.yourpc.util.JDBCUtils;

public class RMAServiceImpl 
implements RMAService {
	
	private static Logger logger = LogManager.getLogger(RMAServiceImpl.class);
	private RMADAO rmaDAO = null;
	
	public RMAServiceImpl() {
		rmaDAO = new RMADAOImpl();
	}

	@Override
	public RMA findById(Long rmaId) throws ServiceException, DataException {

		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			return rmaDAO.findById(conn, rmaId);
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public List<RMA> findBy(RMACriteria criteria) throws ServiceException, DataException {

		Connection conn = null;
		
		try {
			conn = JDBCUtils.getConnection();
			return rmaDAO.findBy(conn, criteria);
		} catch (SQLException e) {
			logger.fatal(e);
			throw new ServiceException(e);
		} finally {
			JDBCUtils.close(conn);
		}
	}

	@Override
	public Long create(RMA rma) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;
		
		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			Long id = rmaDAO.create(conn, rma);
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
	public Boolean update(RMA rma) throws ServiceException, DataException {

		Connection conn = null;
		boolean commit = false;

		try {
			conn = JDBCUtils.getConnection();
			conn.setAutoCommit(JDBCUtils.NO_AUTO_COMMIT);
			if (rmaDAO.update(conn, rma)) {
				commit = true;
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqle) {
			logger.fatal(sqle);
			throw new ServiceException(sqle);
		} finally {
			JDBCUtils.close(conn, commit);
		}
	}
}
