/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.filter.order;

import com.makinus.unitedsupplies.common.data.form.OrderFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static com.makinus.unitedsupplies.common.data.dao.filter.order.OrderFilterCriteriaBuilder.aOrderFilterCriteria;

/**
 * @author abuabdul
 */
@Repository
public class OrderFilterDAOImpl implements OrderFilterDAO {

    private final Logger LOG = LogManager.getLogger(OrderFilterDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> filterOrder(OrderFilterForm orderFilterForm) throws UnitedSuppliesException {
        LOG.info("Open filterOrder method in {}", this.getClass().getSimpleName());
        try {
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Order> query = builder.createQuery(Order.class);
            OrderFilterCriteriaBuilder orderFilterCriteriaBuilder = aOrderFilterCriteria(builder, query)
                    .paymentType(orderFilterForm.getPaymentType())
                    .name(orderFilterForm.getName())
                    .mobile(orderFilterForm.getMobile())
                    .status(orderFilterForm.getStatus())
                    .totalAmount(orderFilterForm.getTotalAmount())
                    .paidAmount(orderFilterForm.getPaidAmount())
                    .orderDate(orderFilterForm.getOrderDate())
                    .dateRange(orderFilterForm.getFromDate(), orderFilterForm.getToDate());

            if (orderFilterCriteriaBuilder.isParamSet()) {
                return entityManager.createQuery(query.where(orderFilterCriteriaBuilder.predicate())).getResultList();
            }
        } catch (ParseException ex) {
            throw new UnitedSuppliesException(ex.getMessage());
        }
        return new ArrayList<>();
    }
}
