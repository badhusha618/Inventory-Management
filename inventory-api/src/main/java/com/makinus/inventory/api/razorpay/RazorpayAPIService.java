/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.razorpay;

import com.makinus.unitedsupplies.common.data.entity.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static com.makinus.inventory.api.utils.ApiUtils.*;


/**
 * @author Bad_sha
 */
@Component
public class RazorpayAPIService implements RazorpayAPI {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RazorpayClient client;

    @Value("${payment.razorpay.secret.key}")
    private String secretKey;

    @Override
    public String createOrder(Order order) throws RazorpayException {
        LOG.info("Create New Razorpay order");
        try {
            com.razorpay.Order razorPayOrder = client.Orders.create(createOrderJSONObject(order));
            return razorPayOrder.get("id");
        } catch (RazorpayException e) {
            LOG.error("Error occurred while making api call {}", e.getMessage());
            Thread.currentThread().interrupt();
            throw new RazorpayException(e.getMessage());
        }
    }

    @Override
    public boolean verifySignature(JSONObject checkoutResponse) throws RazorpayException {
        LOG.info("Verify Signature After Payment");
        try {
            return Utils.verifyPaymentSignature(checkoutResponse, this.secretKey);
        } catch (RazorpayException e) {
            LOG.error("Error occurred while making api call {}", e.getMessage());
            Thread.currentThread().interrupt();
            throw new RazorpayException(e.getMessage());
        }
    }

    private JSONObject createOrderJSONObject(Order order) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("receipt", order.getOrderNo());
        orderRequest.put("amount", valueInPaise(order.getOrderTotal()));
        orderRequest.put("currency", INDIAN_CURRENCY);
        orderRequest.put("payment_capture", AUTO_CAPTURE);
        return orderRequest;
    }

}