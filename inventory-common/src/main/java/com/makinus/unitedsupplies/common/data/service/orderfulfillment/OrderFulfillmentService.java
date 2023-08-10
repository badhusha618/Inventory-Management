/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.orderfulfillment;

import com.makinus.unitedsupplies.common.data.entity.OrderFulfillment;
import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.math.BigDecimal;
import java.util.List;


/** Created by Bad_sha */
public interface OrderFulfillmentService {

  OrderFulfillment saveOrderFulfillment(final OrderFulfillment orderFulfillment);

  OrderFulfillment findOrderFulfillmentById(Long id) throws InventoryException;

  OrderFulfillment findOrderFulfillmentByFulfillmentRef(String fulfillmentRef) throws InventoryException;

  String findLastFulfillmentRefByOrderRef(Long orderRef);

  OrderFulfillment updateOrderFulfillment(final OrderFulfillment orderFulfillment) throws InventoryException;

  List<Tuple<Long, String>> getOrderFulfillmentRefListByOrderRef(Long orderRef);

  List<OrderFulfillment> getOrderFulfillmentListByOrderRef(Long orderRef);

  List<OrderFulfillment> getOrderFulfillmentListByFulfillmentIds(List<Long> fulfillmentIds);

  List<OrderFulfillment> getAllFulfillments();

  List<OrderFulfillment> getOrderFulfillmentListByOrder(Long orderRef);

  List<OrderFulfillment> getGeneratedInvoicesByOrderRef(Long orderRef);

  void updateOrderFulfillmentBasedVendorAllocation(ProductOrder productOrder, BigDecimal saleRate, BigDecimal transportCharge) throws InventoryException;

  boolean isProductInvoiceAvailable(final String invoiceNo, final Long fulfillmentId) throws InventoryException;

  boolean isServiceInvoiceAvailable(final String username, final Long fulfillmentId) throws InventoryException;

}
