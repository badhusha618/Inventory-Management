/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.sms;

import org.springframework.util.concurrent.ListenableFuture;

/**
 * @author Bad_sha
 */
@FunctionalInterface
public interface SMSService {

    ListenableFuture<Integer> sendOTP(String mobile, String sms);
}
