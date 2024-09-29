package com.pinguela.yourpc.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.pinguela.DataException;
import com.pinguela.yourpc.dao.CustomerOrderDAO;
import com.pinguela.yourpc.model.AbstractCriteria;
import com.pinguela.yourpc.model.AbstractUpdateValues;
import com.pinguela.yourpc.model.Customer;
import com.pinguela.yourpc.model.CustomerOrder;
import com.pinguela.yourpc.model.CustomerOrderCriteria;
import com.pinguela.yourpc.model.CustomerOrderRanges;
import com.pinguela.yourpc.model.CustomerOrder_;
import com.pinguela.yourpc.model.Customer_;
import com.pinguela.yourpc.model.OrderState_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerOrderDAOImpl 
extends AbstractMutableDAO<Long, CustomerOrder>
implements CustomerOrderDAO {

	private static Logger logger = LogManager.getLogger(CustomerOrderDAOImpl.class);

	public CustomerOrderDAOImpl() {
	}

	@Override
	public Long create(Session session, CustomerOrder co) 
			throws DataException {
		return super.createEntity(session, co);
	}

	@Override
	public Boolean update(Session session, CustomerOrder co) 
			throws DataException {
		return super.updateEntity(session, co);
	}

	@Override
	public CustomerOrder findById(Session session, Long id) 
			throws DataException {
		return super.findById(session, id);
	}

	@Override
	public List<CustomerOrder> findByCustomer(Session session, Integer customerId) 
			throws DataException {
		CustomerOrderCriteria criteria = new CustomerOrderCriteria();
		criteria.setCustomerId(customerId);
		return super.findBy(session, criteria);
	}

	@Override
	public List<CustomerOrder> findBy(Session session, CustomerOrderCriteria criteria) 
			throws DataException {
		return super.findBy(session, criteria);
	}

	@Override
	protected List<Predicate> getCriteria(CriteriaBuilder builder, 
			Root<CustomerOrder> root, AbstractCriteria<CustomerOrder> criteria) {
		CustomerOrderCriteria coc = (CustomerOrderCriteria) criteria;
		List<Predicate> predicates = new ArrayList<>();

		if (coc.getCustomerId() != null) {
			predicates.add(builder.equal(root.get(CustomerOrder_.customer).get(Customer_.id), coc.getCustomerId()));
		}

		if (coc.getCustomerEmail() != null) {
			Join<CustomerOrder, Customer> joinCustomer = root.join(CustomerOrder_.customer);
			joinCustomer.on(builder.equal(joinCustomer.get(Customer_.email), coc.getCustomerEmail()));
		}

		if (coc.getMinAmount() != null) {
			predicates.add(builder.ge(root.get(CustomerOrder_.totalPrice), coc.getMinAmount()));
		}

		if (coc.getMaxAmount() != null) {
			predicates.add(builder.le(root.get(CustomerOrder_.totalPrice), coc.getMaxAmount()));
		}

		if (coc.getMinDate() != null) {
			predicates.add(builder.greaterThanOrEqualTo(root.get(CustomerOrder_.orderDate), coc.getMinDate()));
		}

		if (coc.getMaxDate() != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(CustomerOrder_.orderDate), coc.getMaxDate()));
		}

		if (coc.getState() != null) {
			predicates.add(builder.equal(root.get(CustomerOrder_.state).get(OrderState_.id), coc.getState()));
		}

		return predicates;
	}

	@Override
	protected void groupByCriteria(CriteriaBuilder builder, CriteriaQuery<CustomerOrder> query,
			Root<CustomerOrder> root, AbstractCriteria<CustomerOrder> criteria) {
		// Unused	
	}

	@Override
	public CustomerOrderRanges getRanges(Session session, CustomerOrderCriteria criteria) 
			throws DataException {

		Object[] results = null;

		try {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Object[]> query = builder.createQuery(Object[].class);
			Root<CustomerOrder> root = query.from(getTargetClass());

			query.multiselect(
					builder.min(root.get(CustomerOrder_.TOTAL_PRICE)),
					builder.max(root.get(CustomerOrder_.TOTAL_PRICE)),
					builder.min(root.get(CustomerOrder_.ORDER_DATE)),
					builder.max(root.get(CustomerOrder_.ORDER_DATE))
					);

			results = session.createQuery(query).getSingleResult();

		} catch (HibernateException e) {
			logger.error(e.getMessage(), e);
			throw new DataException(e);
		}

		CustomerOrderRanges ranges = new CustomerOrderRanges();
		ranges.setMinAmount((Double) results[0]);
		ranges.setMaxAmount((Double) results[1]);
		ranges.setMinDate((Date) results[2]);
		ranges.setMaxDate((Date) results[3]);

		return ranges;
	}

	@Override
	protected void setUpdateValues(CriteriaBuilder builder, CriteriaUpdate<CustomerOrder> updateQuery,
			Root<CustomerOrder> root, AbstractUpdateValues<CustomerOrder> updateValues) {
		// Unused
	}

}
