package com.pinguela.yourpc.service.impl;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.ServiceException;
import com.pinguela.yourpc.dao.TicketTypeDAO;
import com.pinguela.yourpc.dao.impl.TicketTypeDAOImpl;
import com.pinguela.yourpc.model.EntityType;
import com.pinguela.yourpc.model.Ticket;
import com.pinguela.yourpc.service.TicketTypeService;
import com.pinguela.yourpc.util.HibernateUtils;

public class TicketTypeServiceImpl implements TicketTypeService {
    
    private static Logger logger = LogManager.getLogger(TicketTypeServiceImpl.class);
    
    private TicketTypeDAO ticketTypeDAO;
    
    public TicketTypeServiceImpl() {
        ticketTypeDAO = new TicketTypeDAOImpl();
    }

    @Override
    public Map<String, EntityType<Ticket>> findAll() throws ServiceException, DataException {
        Session session = null;

        try {
            session = HibernateUtils.openSession();
            return ticketTypeDAO.findAll(session);
        } catch (HibernateException e) {
            logger.fatal(e.getMessage(), e);
            throw new ServiceException(e);
        } finally {
            HibernateUtils.close(session);
        }
    }
    
}
