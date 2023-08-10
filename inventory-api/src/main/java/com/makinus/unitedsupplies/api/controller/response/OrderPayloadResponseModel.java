/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller.response;

import com.makinus.unitedsupplies.api.paytm.PaytmPayloadResponseModel;
import com.makinus.unitedsupplies.api.razorpay.RazorpayPayloadResponseModel;

/** Created by ibrahim */
public class OrderPayloadResponseModel {

  private String paymentProvider;
  private PaytmPayloadResponseModel paytmPayloadResponseModel;
  private RazorpayPayloadResponseModel razorpayPayloadResponseModel;

  public String getPaymentProvider() {
    return paymentProvider;
  }

  public void setPaymentProvider(String paymentProvider) {
    this.paymentProvider = paymentProvider;
  }

  public PaytmPayloadResponseModel getPaytmPayloadResponseModel() {
    return paytmPayloadResponseModel;
  }

  public void setPaytmPayloadResponseModel(PaytmPayloadResponseModel paytmPayloadResponseModel) {
    this.paytmPayloadResponseModel = paytmPayloadResponseModel;
  }

  public RazorpayPayloadResponseModel getRazorpayPayloadResponseModel() {
    return razorpayPayloadResponseModel;
  }

  public void setRazorpayPayloadResponseModel(RazorpayPayloadResponseModel razorpayPayloadResponseModel) {
    this.razorpayPayloadResponseModel = razorpayPayloadResponseModel;
  }

  @Override
  public String toString() {
    return "OrderPayloadResponseModel{" +
            "paymentProvider='" + paymentProvider + '\'' +
            ", paytmPayloadResponseModel=" + paytmPayloadResponseModel +
            ", razorpayPayloadResponseModel=" + razorpayPayloadResponseModel +
            '}';
  }
}
