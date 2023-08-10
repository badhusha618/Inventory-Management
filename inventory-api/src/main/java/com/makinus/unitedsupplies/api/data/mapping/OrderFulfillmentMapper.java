/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.OrderFulfillment;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by sabique
 */
@Component
@Qualifier("OrderFulfillmentMapper")
public class OrderFulfillmentMapper implements EntityMapper<Order, OrderFulfillment> {

    private final Logger LOG = LogManager.getLogger(OrderFulfillmentMapper.class);

    @Override
    public OrderFulfillment map(Order order) throws UnitedSuppliesException {
        LOG.info("Map Order Fulfillment by Order Entity");
        try {
            OrderFulfillment orderFulfillment = new OrderFulfillment();
            orderFulfillment.setFulfillmentRef(FULFILLMENT_REF_INIT);
            orderFulfillment.setOrderRef(order.getOrderRef());
            orderFulfillment.setSubTotal(order.getSubTotal());
            orderFulfillment.setTransportCharges(order.getTransportCharges());
            orderFulfillment.setServiceCharges(order.getServiceCharges());
            orderFulfillment.setLoadingCharges(order.getLoadingCharges());
            orderFulfillment.setOrderTotal(order.getOrderTotal());
            orderFulfillment.setCreatedBy(getCurrentUser());
            orderFulfillment.setCreatedDate(getInstant());
            orderFulfillment.setUpdatedBy(getCurrentUser());
            orderFulfillment.setUpdatedDate(getInstant());
            return orderFulfillment;
        } catch (Exception e) {
            LOG.warn("Order Fulfillment Mapper throws exception {}", e.getMessage());
            throw new UnitedSuppliesException(e.getMessage());
        }
    }

}
