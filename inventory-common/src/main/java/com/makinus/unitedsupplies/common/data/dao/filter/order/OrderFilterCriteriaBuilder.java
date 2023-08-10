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

import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.reftype.OrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static com.makinus.unitedsupplies.common.utils.AppUtils.utcDateForDDMMYYYY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author abuabdul
 */
public class OrderFilterCriteriaBuilder {

    private static final String USER = "user";

    private CriteriaBuilder builder;
    private Predicate predicate;
    private Root<Order> orderRoot;
    private Join<Order, User> userJoin;
    private boolean paramSet = Boolean.FALSE;

    private OrderFilterCriteriaBuilder(CriteriaBuilder builder, CriteriaQuery<Order> query) {
        this.builder = builder;
        this.predicate = builder.conjunction();
        this.orderRoot = query.from(Order.class);
        this.userJoin = this.orderRoot.join(USER);
    }

    public static OrderFilterCriteriaBuilder aOrderFilterCriteria(CriteriaBuilder builder, CriteriaQuery<Order> query) {
        return new OrderFilterCriteriaBuilder(builder, query);
    }

    public OrderFilterCriteriaBuilder paymentType(String paymentType) {
        if (isNotEmpty(paymentType)) {
            predicate = builder.and(predicate, builder.equal(orderRoot.get("paymentType"), paymentType));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder name(String name) {
        if (isNotEmpty(name)) {
            predicate = builder.and(predicate, builder.like(userJoin.get("fullName"), "%" + name + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder mobile(String mobile) {
        if (isNotEmpty(mobile)) {
            predicate = builder.and(predicate, builder.like(userJoin.get("mobile"), "%" + mobile + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder status(String status) {
        if (isNotEmpty(status)) {
            String statusValue = OrderStatus.statusMatch(status).getStatus();
            predicate = builder.and(predicate, builder.equal(orderRoot.get("status"), statusValue));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder totalAmount(String totalAmount) {
        if (isNotEmpty(totalAmount)) {
            predicate = builder.and(predicate, builder.equal(orderRoot.get("orderTotal"), (new BigDecimal(totalAmount))));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder paidAmount(String paidAmount) {
        if (isNotEmpty(paidAmount)) {
            predicate = builder.and(predicate, builder.equal(orderRoot.get("paidAmount"), (new BigDecimal(paidAmount))));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder orderDate(String orderDate) throws ParseException {
        if (isNotEmpty(orderDate)) {
            Date orderedDate = utcDateForDDMMYYYY(orderDate);
            predicate = builder.and(predicate, builder.equal(orderRoot.get("orderDate"), orderedDate));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public OrderFilterCriteriaBuilder dateRange(String fromDate, String toDate) throws ParseException {
        if (isNotEmpty(fromDate) && isNotEmpty(toDate)) {
            Date from = utcDateForDDMMYYYY(fromDate);
            Date to = utcDateForDDMMYYYY(toDate);
            predicate = builder.and(predicate, builder.between(orderRoot.get("createdDate"), from, to));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public Predicate predicate() {
        predicate = builder.and(predicate, builder.equal(orderRoot.get("deleted"), YNStatus.NO.getStatus()));
        return this.predicate;
    }

    public boolean isParamSet() {
        return this.paramSet;
    }
}
