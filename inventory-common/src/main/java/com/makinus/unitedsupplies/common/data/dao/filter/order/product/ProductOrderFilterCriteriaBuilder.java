/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao.filter.order.product;

import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.reftype.OrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.ProdOrderStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Bad_sha
 */
public class ProductOrderFilterCriteriaBuilder {

    private CriteriaBuilder builder;
    private Predicate predicate;
    private Root<ProductOrder> productOrderRoot;
    private boolean paramSet = Boolean.FALSE;

    private ProductOrderFilterCriteriaBuilder(CriteriaBuilder builder, CriteriaQuery<ProductOrder> query) {
        this.builder = builder;
        this.predicate = builder.conjunction();
        this.productOrderRoot = query.from(ProductOrder.class);
    }

    public static ProductOrderFilterCriteriaBuilder aProductOrderFilterCriteria(CriteriaBuilder builder, CriteriaQuery<ProductOrder> query) {
        return new ProductOrderFilterCriteriaBuilder(builder, query);
    }

    public ProductOrderFilterCriteriaBuilder productCode(String productCode) {
        if (isNotEmpty(productCode)) {
            predicate = builder.and(predicate, builder.equal(productOrderRoot.get("productCode"), productCode));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductOrderFilterCriteriaBuilder productName(String productName) {
        if (isNotEmpty(productName)) {
            predicate = builder.and(predicate, builder.like(productOrderRoot.get("productName"), "%" + productName + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductOrderFilterCriteriaBuilder vendorCode(String vendorCode) {
        if (isNotEmpty(vendorCode)) {
            predicate = builder.and(predicate, builder.like(productOrderRoot.get("vendorCode"), vendorCode));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductOrderFilterCriteriaBuilder status(String status) {
        if (isNotEmpty(status)) {
            String statusValue = ProdOrderStatus.statusMatch(status).getStatus();
            predicate = builder.and(predicate, builder.equal(productOrderRoot.get("status"), statusValue));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public ProductOrderFilterCriteriaBuilder orderRefIn(List<Long> orderRefList) throws ParseException {
        if (!orderRefList.isEmpty()) {
            predicate = builder.and(predicate, productOrderRoot.get("orderRef").in(orderRefList));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public Predicate predicate() {
        return this.predicate;
    }

    public boolean isParamSet() {
        return this.paramSet;
    }
}
