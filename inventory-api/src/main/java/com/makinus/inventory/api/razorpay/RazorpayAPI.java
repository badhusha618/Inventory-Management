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
import com.razorpay.RazorpayException;
import org.json.JSONObject;

/**
 * @author Bad_sha
 */
public interface RazorpayAPI {

    String createOrder(Order order) throws RazorpayException;

    boolean verifySignature(JSONObject jsonObject) throws RazorpayException;

}