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

import com.makinus.unitedsupplies.common.data.dao.UserCartRepository;
import com.makinus.unitedsupplies.common.data.entity.UserCart;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.loadingCharges.LoadingChargesService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by sabique
 */
@Service
@Transactional
public class UserCartServiceImpl implements UserCartService {

    private final Logger LOG = LogManager.getLogger(UserCartServiceImpl.class);

    private final UserCartRepository userCartRepository;

    private final LoadingChargesService loadingChargesService;

    public UserCartServiceImpl(@Autowired UserCartRepository userCartRepository, LoadingChargesService loadingChargesService) {
        this.userCartRepository = userCartRepository;
        this.loadingChargesService = loadingChargesService;
    }

    @Override
    public UserCart findCartById(Long id) throws UnitedSuppliesException {
        LOG.info("Get UserCart from the database");
        Optional<UserCart> cartOptional = userCartRepository.findById(id);
        if (cartOptional.isPresent()) {
            return cartOptional.get();
        }
        throw new UnitedSuppliesException(String.format("Cart is not found with the id %d", id));
    }

    @Override
    public UserCart saveUserCart(UserCart userCart) {
        LOG.info("Saving UserCart in the database");
        processLoadingCharge(userCart);
        return userCartRepository.save(userCart);
    }

    @Override
    public List<UserCart> saveUserCartList(List<UserCart> userCartList) {
        LOG.info("Saving List of UserCart in the database");
        return userCartRepository.saveAll(userCartList);
    }

    @Override
    public List<UserCart> getAllUserCart(Long userId) {
        LOG.info("Saving All UserCart in the database");
        return userCartRepository.listAllCartByUserId(userId);
    }

    @Override
    public List<UserCart> removeAllUserCart(Long userId) {
        List<UserCart> userCartList = userCartRepository.listAllCartByUserId(userId);
        userCartList.forEach(
                userCart -> {
                    userCart.setDeleted(YNStatus.YES.getStatus());
                    userCart.setUpdatedBy(getCurrentUser());
                    userCart.setUpdatedDate(getInstant());
                });
        return userCartList;
    }

    @Override
    public Optional<UserCart> getCartByProductIdAndUserId(Long userId, Long productId) {
        return userCartRepository.findCartByUserIdAndProdId(userId, productId);
    }

    @Override
    public UserCart findCartByProductIdAndUserId(Long userId, Long productId)
            throws UnitedSuppliesException {
        Optional<UserCart> userCartOptional = userCartRepository.findCartByUserIdAndProdId(userId, productId);
        if (userCartOptional.isPresent()) {
            return userCartOptional.get();
        }
        throw new UnitedSuppliesException(
                String.format(
                        "Cart Item is not found with the user id %d, product id %d", userId, productId));
    }

    @Override
    public UserCart removeCartByProductIdAndUserId(Long userId, Long productId)
            throws UnitedSuppliesException {
        Optional<UserCart> userCartOptional =
                userCartRepository.findCartByUserIdAndProdId(userId, productId);
        if (userCartOptional.isPresent()) {
            UserCart userCart = userCartOptional.get();
            userCart.setDeleted(YNStatus.YES.getStatus());
            userCart.setUpdatedBy(getCurrentUser());
            userCart.setUpdatedDate(getInstant());
            return userCart;
        }
        throw new UnitedSuppliesException(
                String.format("Cart Items are not found with the id %d", userId));
    }

    private void processLoadingCharge(UserCart userCart) {
        Optional<Tuple<BigDecimal, Integer>> optionalCharge = loadingChargesService.availableQuantityListByProduct(userCart.getQuantity(), userCart.getProductId());
        if (optionalCharge.isPresent()) {
            userCart.setLoadingCharges(optionalCharge.get().getA() != null ? optionalCharge.get().getA() : new BigDecimal(0));
        } else {
            userCart.setLoadingCharges(BigDecimal.ZERO);
        }
    }
}
