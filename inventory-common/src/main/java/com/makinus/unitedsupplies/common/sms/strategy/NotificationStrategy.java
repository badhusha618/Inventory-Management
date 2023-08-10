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

import org.springframework.util.concurrent.ListenableFuture;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

/**
 * @author abuabdul
 */
public interface NotificationStrategy<T> {

    String END_POINT = "https://api.textlocal.in/send/?";

    String POST = "POST";
    String APIKEY = "apiKey";
    String MESSAGE = "message";
    String SENDER = "sender";
    String NUMBERS = "numbers";
    String UNICODE = "unicode";
    String TEST = "test";

    ListenableFuture<T> sendSMS(final String mobileNo, final String method, final String sms) throws NotificationException;

    HttpURLConnection getConnection() throws NotificationException;

    default String inputStreamToString(final InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
    }

}