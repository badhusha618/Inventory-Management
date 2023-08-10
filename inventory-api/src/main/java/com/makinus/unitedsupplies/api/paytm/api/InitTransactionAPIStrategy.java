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

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** @author abuabdul */
@Component
@Qualifier("InitTransactionAPIStrategy")
public class InitTransactionAPIStrategy implements HttpClientAPIStrategy {

  private final Logger LOG = LogManager.getLogger(this.getClass());

  @Value("${us.app.paytm.init.tx.url}")
  private String paytmTxUrl;

  @Override
  public HttpURLConnection getConnection(String... params) throws HttpClientAPIException {
    LOG.info("Get connection for sending transaction request");
    try {
      URL url = new URL(String.format(paytmTxUrl, params[0], params[1]));
      return (HttpURLConnection) url.openConnection();
    } catch (IOException io) {
      throw new HttpClientAPIException(io.getMessage(), io);
    }
  }

  @Override
  public String makeApiCall(String body, String... params) throws HttpClientAPIException {
    try {
      HttpURLConnection connection = getConnection(params);
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");
      connection.setDoOutput(true);
      DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
      outputStream.writeBytes(body);
      outputStream.flush();
      outputStream.close();

      int responseCode = connection.getResponseCode();
      String response;
      if (responseCode == 200) {
        response = inputStreamToString(connection.getInputStream());
        LOG.info("Invoked Transaction Status API, response: {}", response);
      } else {
        response = inputStreamToString(connection.getErrorStream());
        LOG.info("Unable to make Transaction Status API, response: {}", response);
      }
      return response;
    } catch (IOException io) {
      LOG.warn("Exception Occurred while making call to Paytm {}", io.getMessage());
      throw new HttpClientAPIException(io.getMessage(), io);
    }
  }
}
