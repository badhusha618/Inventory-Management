/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {

    @Value("${payment.razorpay.api.key}")
    private String apiKey;

    @Value("${payment.razorpay.secret.key}")
    private String secretKey;

    @Bean
    public RazorpayClient razorPayClient() throws RazorpayException {
        return new RazorpayClient(this.apiKey, this.secretKey);
    }
}
