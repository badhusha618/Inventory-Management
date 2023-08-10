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

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabique
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CartResponse {

    private List<CartProductResponse> cartProducts = new ArrayList<>();

    private String totalAmount;

    public List<CartProductResponse> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProductResponse> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

}
