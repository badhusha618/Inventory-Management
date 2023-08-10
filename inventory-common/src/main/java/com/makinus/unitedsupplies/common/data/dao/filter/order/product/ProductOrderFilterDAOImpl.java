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

import com.makinus.unitedsupplies.common.data.dao.OrderRepository;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.form.ProductOrderFilterForm;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.data.dao.filter.order.product.ProductOrderFilterCriteriaBuilder.aProductOrderFilterCriteria;
import static com.makinus.unitedsupplies.common.utils.AppUtils.utcDateForDDMMYYYY;

/**
 * @author abuabdul
 */
@Repository
public class ProductOrderFilterDAOImpl implements ProductOrderFilterDAO {

    private final Logger LOG = LogManager.getLogger(ProductOrderFilterDAOImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<ProductOrder> filterProductOrder(ProductOrderFilterForm productOrderFilterForm) throws UnitedSuppliesException {
        LOG.info("Open filterProductOrder method in {}", this.getClass().getSimpleName());
        try {
            List<Long> orderRefList = new ArrayList<>();
            if (StringUtils.isNotEmpty(productOrderFilterForm.getFromOrderDate()) && StringUtils.isNotEmpty(productOrderFilterForm.getToOrderDate())) {
                Date from = utcDateForDDMMYYYY(productOrderFilterForm.getFromOrderDate());
                Date to = utcDateForDDMMYYYY(productOrderFilterForm.getToOrderDate());
                orderRefList.addAll(orderRepository.findOrdersByOrderDate(from, to).stream().map(Order::getOrderRef).collect(Collectors.toList()));
            }
            CriteriaBuilder builder = entityManager.getCriteriaBuilder();
            CriteriaQuery<ProductOrder> query = builder.createQuery(ProductOrder.class);
            ProductOrderFilterCriteriaBuilder productOrderFilterCriteriaBuilder = aProductOrderFilterCriteria(builder, query)
                    .productCode(productOrderFilterForm.getProductCode())
                    .productName(productOrderFilterForm.getProductName())
                    .vendorCode(productOrderFilterForm.getVendorCode())
                    .status(productOrderFilterForm.getStatus())
                    .orderRefIn(orderRefList);

            if (productOrderFilterCriteriaBuilder.isParamSet()) {
                return entityManager.createQuery(query.where(productOrderFilterCriteriaBuilder.predicate())).getResultList();
            }
        } catch (ParseException ex) {
            throw new UnitedSuppliesException(ex.getMessage());
        }
        return new ArrayList<>();
    }
}
