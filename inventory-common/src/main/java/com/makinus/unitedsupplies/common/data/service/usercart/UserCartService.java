/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.usercart;

import com.makinus.unitedsupplies.common.data.entity.UserCart;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import java.util.List;
import java.util.Optional;

/**
 * Created by sabique
 */
public interface UserCartService {

    UserCart findCartById(Long id) throws UnitedSuppliesException;

    UserCart saveUserCart(UserCart userCart);

    List<UserCart> saveUserCartList(List<UserCart> userCart);

    List<UserCart> getAllUserCart(Long id) throws UnitedSuppliesException;

    Optional<UserCart> getCartByProductIdAndUserId(Long userId, Long productId);

    UserCart findCartByProductIdAndUserId(Long userId, Long productId) throws UnitedSuppliesException;

    List<UserCart> removeAllUserCart(Long id) throws UnitedSuppliesException;

    UserCart removeCartByProductIdAndUserId(Long id, Long productId) throws UnitedSuppliesException;
}
