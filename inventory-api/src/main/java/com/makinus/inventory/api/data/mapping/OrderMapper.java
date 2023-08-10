/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.data.mapping;

import com.makinus.inventory.api.controller.request.OrderRequest;
import com.makinus.inventory.api.controller.response.OrderResponse;
import com.makinus.inventory.api.controller.response.ProductOrderResponse;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.mapper.ListEntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.*;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author Bad_sha
 */
@Component
@Qualifier("OrderMapper")
public class OrderMapper implements EntityWithExtraValueMapper<OrderRequest, User, Order>, ListEntityWithExtraValueMapper<List<Order>, Map<Long, List<ProductOrder>>, OrderResponse> {

    private final Logger LOG = LogManager.getLogger(OrderMapper.class);

    @Override
    public Order mapExtra(OrderRequest orderRequest, final User user) throws InventoryException {
        LOG.info("Map Order Request to Order Entity");
        try {
            Order order = new Order();
            order.setUser(user);
            order.setOrderDate(getInstant());
            order.setOrderNo("US" + randomNumeric(7));  // TODO: Have to update this logic
            order.setDelAddressId(stringToLong(orderRequest.getDeliveryAddressId()));
            order.setBillAddressId(stringToLong(orderRequest.getBillingAddressId()));
            order.setSubTotal(orderRequest.getProductPrice());
            order.setTransportCharges(orderRequest.getTransportCharge());
            order.setServiceCharges(orderRequest.getServiceCharge());
            order.setLoadingCharges(orderRequest.getLoadingCharge());
            order.setOrderTotal(orderRequest.getTotalCharges());
            order.setPaymentType(orderRequest.getPaymentType());
            order.setPaidAmount(orderRequest.getPaidAmount());
            order.setPaymentStatus(PaymentStatus.NOT_PAID.getStatus());
            order.setPaymentProvider(PaymentProvider.statusMatch(orderRequest.getPaymentProvider()).getStatus());
            order.setIsRead(YNStatus.NO.getStatus());
            order.setCreatedBy(getCurrentUser());
            order.setCreatedDate(getInstant());
            order.setUpdatedBy(getCurrentUser());
            order.setUpdatedDate(getInstant());
            order.setStatus(OrderStatus.NEW.getStatus());
            order.setDeleted(YNStatus.NO.getStatus());
            return order;
        } catch (Exception e) {
            LOG.warn("Order Mapper throws exception {}", e.getMessage());
            throw new InventoryException(e.getMessage());
        }
    }

    @Override
    public List<OrderResponse> map(List<Order> orders, Map<Long, List<ProductOrder>> productOrdersMap) throws InventoryException {
        LOG.info("Map Order entities to Order responses");
        List<OrderResponse> orderResponses = new ArrayList<>();
        orders.forEach(order -> {
            OrderResponse orderResponse = new OrderResponse();
            orderResponse.setOrderRef(longToString(order.getOrderRef()));
            orderResponse.setOrderNo(ORDER_REF_NO_PREFIX + longToString(order.getOrderRef()));
            orderResponse.setOrderDate(utcDateForDDMMYYYY(order.getOrderDate()));
            orderResponses.add(orderResponse);
        });
        return orderResponses;
    }

    private List<ProductOrderResponse> remapProductOrders(List<ProductOrder> productOrders) {
        List<ProductOrderResponse> productOrderResponses = new ArrayList<>();
        productOrders.forEach(productOrder -> {
            ProductOrderResponse productOrderResponse = new ProductOrderResponse();
            productOrderResponse.setProductId(longToString(productOrder.getOrderRef()));
            productOrderResponse.setProductName(productOrder.getProductName());
            productOrderResponse.setProductCode(productOrder.getProductCode());
            productOrderResponse.setProductStatus(ProdOrderStatus.statusMatch(productOrder.getStatus()).getDisplay());
            productOrderResponses.add(productOrderResponse);
        });
        return productOrderResponses;
    }

}
