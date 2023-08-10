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

import com.makinus.inventory.api.razorpay.request.RazorpayPaymentRequest;
import com.makinus.inventory.api.utils.ApiUtils;
import com.makinus.unitedsupplies.common.data.entity.RazorpayPayment;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.reftype.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;


/**
 * @author Bad_sha
 */
@Component
@Qualifier("RazorpayPaymentMapper")
public class RazorpayPaymentMapper implements EntityMapper<RazorpayPaymentRequest, RazorpayPayment> {

    private final Logger LOG = LoggerFactory.getLogger(RazorpayPaymentMapper.class);

    @Override
    public RazorpayPayment map(RazorpayPaymentRequest razorpayPaymentRequest) {
        LOG.info("Map Razorpay Payment Request to RazorpayPayment Entity");
        RazorpayPayment razorpayPayment = new RazorpayPayment();
        razorpayPayment.setOrderNo(razorpayPaymentRequest.getOrderNo());
        razorpayPayment.setOrderId(Long.valueOf(razorpayPaymentRequest.getOrderID()));
        razorpayPayment.setRazorOrderID(razorpayPaymentRequest.getRazorOrderID());
        razorpayPayment.setRazorPaymentID(razorpayPaymentRequest.getRazorPaymentID());
        razorpayPayment.setRazorSignature(razorpayPaymentRequest.getRazorSignature());
        razorpayPayment.setAmount(new BigDecimal(razorpayPaymentRequest.getAmount()));
        razorpayPayment.setCurrency(ApiUtils.INDIAN_CURRENCY);
        razorpayPayment.setReason(razorpayPaymentRequest.getReason());
        razorpayPayment.setDescription(PaymentStatus.PAYMENT_SUCCESS.getValue());
        razorpayPayment.setCreatedBy(getCurrentUser());
        razorpayPayment.setCreatedDate(getInstant());
        return razorpayPayment;
    }

}
