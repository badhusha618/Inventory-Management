/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.sms.strategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author abuabdul
 */
@Async
@Component
public class SMSNotificationStrategy implements NotificationStrategy<Integer> {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Value("${sms.apikey}")
    private String smsAPIKey;

    @Value("${sms.sender}")
    private String smsAPISender;

    @Value("${sms.test}")
    private String smsAPITest;

    @Override
    public HttpURLConnection getConnection() throws NotificationException {
        LOG.info("Get connection for sending SMS request");
        try {
            URL url = new URL(END_POINT);
            return (HttpURLConnection) url.openConnection();
        } catch (IOException io) {
            throw new NotificationException(io.getMessage(), io);
        }
    }

    @Override
    public ListenableFuture<Integer> sendSMS(String mobileNo, String method, String sms) throws NotificationException {
        LOG.info("SMS confirmation for order with mobile number {}", mobileNo);
        try {
            HttpURLConnection connection = getConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod(method);
            String data = ParamStringBuilder.getParamsString(parameters(mobileNo, sms));
            connection.setRequestProperty("Content-Length", Integer.toString(data.length()));
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(data);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String response = inputStreamToString(connection.getInputStream());
                LOG.info("SMS sent for delivery, response: {}", response);
            } else {
                String response = inputStreamToString(connection.getErrorStream());
                LOG.info("Unable to send SMS, response: {}", response);
            }
            return new AsyncResult<>(responseCode);
        } catch (IOException io) {
            throw new NotificationException(io.getMessage(), io);
        }
    }

    private Map<String, String> parameters(String mobileNo, String sms) {
        Map<String, String> parameters = new HashMap<>();
        parameters.put(APIKEY, smsAPIKey);
        parameters.put(MESSAGE, sms);
        parameters.put(SENDER, smsAPISender);
        parameters.put(NUMBERS, mobileNo);
        parameters.put(UNICODE, Boolean.TRUE.toString());
        parameters.put(TEST, smsAPITest);
        return parameters;
    }

}