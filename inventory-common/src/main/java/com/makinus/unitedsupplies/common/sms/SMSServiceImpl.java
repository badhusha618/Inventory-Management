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

import com.makinus.unitedsupplies.common.sms.strategy.NotificationException;
import com.makinus.unitedsupplies.common.sms.strategy.NotificationStrategy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import static com.makinus.unitedsupplies.common.sms.strategy.NotificationStrategy.POST;

/**
 * @author abuabdul
 */
@Async
@Component
public class SMSServiceImpl implements SMSService {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Autowired
    private NotificationStrategy<Integer> notificationStrategy;

    @Override
    public ListenableFuture<Integer> sendOTP(String mobile, String sms) {
        try {
            return notificationStrategy.sendSMS(mobile, POST, sms);
        } catch (NotificationException e) {
            LOG.error("Error occurred while sending sms {}", e.getMessage());
        }
        return new AsyncResult<>(HttpStatus.NO_CONTENT.value());
    }
}
