/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.order;

import com.makinus.unitedsupplies.common.data.form.OrderFilterForm;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.reftype.OrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.PaymentType;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author Bad_sha
 */
public interface OrderService {

    Order saveOrder(Order order);

    List<Order> ordersList();

    List<Order> ordersListAll();

    List<Order> newOrderList();

    List<Tuple<Long, Integer>> orderProdOrderCount();

    List<Order> ordersListByUser(Long userId);

    Order findOrderByOrderRef(Long orderRef) throws InventoryException;

    Order updateOrder(final Order order) throws InventoryException;

    void updateOrdersBasedVendorAllocation(ProductOrder oldProductOrder, BigDecimal saleRate, BigDecimal transportCharge, String vendorId) throws InventoryException;

    void updateOrderList(List<Order> orders);

    Order findOrder(final Long id) throws InventoryException;

    Order removeOrder(final Long id) throws InventoryException;

    Order orderDetailsUpdated(Long ref) throws InventoryException;

    Order updateOrderStatus(Long ref, OrderStatus orderStatus) throws InventoryException;

    Order updatePaymentType(Long id, PaymentType paymentType) throws InventoryException;

    List<Order> filterOrder(OrderFilterForm orderFilterForm) throws InventoryException;

    boolean isCustomerInvoiceAvailable(final String username, Long orderRef) throws InventoryException;
}
