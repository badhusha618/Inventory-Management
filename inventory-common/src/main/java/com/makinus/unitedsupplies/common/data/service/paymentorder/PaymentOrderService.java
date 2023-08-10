/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.paymentorder;

import com.makinus.unitedsupplies.common.data.entity.PaymentOrder;
import com.makinus.unitedsupplies.common.exception.InventoryException;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface PaymentOrderService {

    PaymentOrder savePaymentOrder(PaymentOrder paymentOrder);

    PaymentOrder saveOrUpdatePaymentOrder(PaymentOrder paymentOrder);

    List<PaymentOrder> paymentOrdersList();

    Optional<PaymentOrder> paymentOrderByOrderRef(Long orderId) throws InventoryException;

    PaymentOrder updatePaymentOrder(final PaymentOrder paymentOrder) throws InventoryException;

    PaymentOrder findPaymentOrder(final Long id) throws InventoryException;

    PaymentOrder removePaymentOrder(final Long id) throws InventoryException;
}
