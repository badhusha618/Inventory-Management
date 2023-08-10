/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.paytm.api;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Scanner;

/** @author abuabdul */
public interface HttpClientAPIStrategy {

  String makeApiCall(String body, String... params) throws HttpClientAPIException;

  HttpURLConnection getConnection(String... params) throws HttpClientAPIException;

  default String inputStreamToString(final InputStream inputStream) {
    StringBuilder stringBuilder = new StringBuilder();
    Scanner scanner = new Scanner(inputStream);
    while (scanner.hasNext()) {
      stringBuilder.append(scanner.nextLine());
    }
    return stringBuilder.toString();
  }
}
