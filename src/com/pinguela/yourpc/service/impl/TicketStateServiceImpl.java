package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketStateDAO;
import com.pinguela.yourpc.dao.impl.TicketStateDAOImpl;
import com.pinguela.yourpc.model.EntityState;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketStateService;
import com.pinguela.yourpc.util.HibernateUtils;

public class TicketStateServiceImpl implements TicketStateService {
    
    private static Logger logger = LogManager.getLogger(TicketStateServiceImpl.class);
    
    private TicketStateDAO ticketStateDAO;
    
    public TicketStateServiceImpl() {
        ticketStateDAO = new TicketStateDAOImpl();
    }

    @Override
    public Map<String, EntityState<Ticket>> findAll() throws ServiceException, DataException {
        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return ticketStateDAO.findAll(session);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
    
}
