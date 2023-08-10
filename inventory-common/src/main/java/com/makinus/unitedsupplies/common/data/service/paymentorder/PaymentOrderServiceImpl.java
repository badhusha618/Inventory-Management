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

import com.makinus.unitedsupplies.common.data.dao.PaymentOrderRepository;
import com.makinus.unitedsupplies.common.data.entity.PaymentOrder;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;

/**
 * Created by abuabdul
 */
@Service
@Transactional
public class PaymentOrderServiceImpl implements PaymentOrderService {

    private final Logger LOG = LogManager.getLogger(PaymentOrderServiceImpl.class);

    private final PaymentOrderRepository paymentOrderRepository;

    public PaymentOrderServiceImpl(@Autowired PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @Override
    public PaymentOrder savePaymentOrder(PaymentOrder paymentOrder) {
        LOG.info("Add New Payment Order in the database");
        PaymentOrder savedPaymentOrder = paymentOrderRepository.save(paymentOrder);
        LOG.info("Saved New Payment Order in the database");
        return savedPaymentOrder;
    }

    @Override
    public PaymentOrder saveOrUpdatePaymentOrder(PaymentOrder paymentOrder) {
        LOG.info("Add New Payment Order in the database");
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public List<PaymentOrder> paymentOrdersList() {
        LOG.info("List Payment Orders from database");
        return paymentOrderRepository.listAllPaymentOrders();
    }

    @Override
    public Optional<PaymentOrder> paymentOrderByOrderRef(Long orderRef) {
        LOG.info("Get Payment Order By Order ref from database");
        return paymentOrderRepository.findPaymentOrderByOrderRef(orderRef);
    }

    @Override
    public PaymentOrder updatePaymentOrder(PaymentOrder paymentOrder) {
        LOG.info("Update Payment Order in the database");
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder findPaymentOrder(Long id) throws UnitedSuppliesException {
        Optional<PaymentOrder> paymentOrderOptional = paymentOrderRepository.findById(id);
        if (paymentOrderOptional.isPresent()) {
            return paymentOrderOptional.get();
        }
        throw new UnitedSuppliesException(format("Payment Order is not found with the id %d", id));
    }

    @Override
    public PaymentOrder removePaymentOrder(Long id) throws UnitedSuppliesException {
        Optional<PaymentOrder> paymentOrderOptional = paymentOrderRepository.findById(id);
        if (paymentOrderOptional.isPresent()) {
            PaymentOrder paymentOrder = paymentOrderOptional.get();
            paymentOrder.setUpdatedBy(getCurrentUser());
            paymentOrder.setUpdatedDate(getInstant());
            return paymentOrder;
        }
        throw new UnitedSuppliesException(format("Payment Order is not found with the id %s", id));
    }
}
