/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.data.mapping;

import com.makinus.unitedsupplies.api.controller.request.CartRequest;
import com.makinus.unitedsupplies.common.data.entity.Transport;
import com.makinus.unitedsupplies.common.data.entity.UserCart;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.AddressCategory;
import com.makinus.unitedsupplies.common.data.service.address.AddressService;
import com.makinus.unitedsupplies.common.data.service.productvendor.ProductVendorService;
import com.makinus.unitedsupplies.common.data.service.transport.TransportService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.NO;
import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by sabique
 */
@Component
@Qualifier("UserCartMapper")
public class UserCartMapper implements EntityWithExtraValueMapper<CartRequest, Long, UserCart>, EntityUpdateMapper<CartRequest, UserCart> {

    private final Logger LOG = LogManager.getLogger(UserCartMapper.class);

    @Autowired
    private TransportService transportService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ProductVendorService productVendorService;

    @Override
    public UserCart mapExtra(CartRequest cartRequest, Long userId) throws UnitedSuppliesException {
        LOG.info("Map Cart Request to Cart Entity");
        String destinationPincode = addressService.findDefaultAddressByUserAndCategory(userId, AddressCategory.DELIVERY_ADDRESS.getStatus()).get().getPostalCode();
        String sourcePincode = productVendorService.findProductVendor(Long.valueOf(cartRequest.getProductVendorId())).getPinCode();
        Integer toAddressDistance = getDistanceByPincodeRange(sourcePincode, destinationPincode);
        UserCart userCart = new UserCart();
        userCart.setUserId(userId);
        userCart.setProductId(stringToLong(cartRequest.getProductId()));
        userCart.setProdVendorId(stringToLong(cartRequest.getProductVendorId()));
        userCart.setQuantity(stringToInt(cartRequest.getQuantity()));
        userCart.setUnitId(stringToLong(cartRequest.getUnitId()));
        userCart.setTransCharges(findTransportCharge(cartRequest.getTransGroup(), Integer.valueOf(cartRequest.getQuantity()), toAddressDistance));
        userCart.setCreatedBy(getCurrentUser());
        userCart.setCreatedDate(getInstant());
        userCart.setUpdatedBy(getCurrentUser());
        userCart.setUpdatedDate(getInstant());
        userCart.setDeleted(NO.getStatus());
        return userCart;
    }

    private BigDecimal findTransportCharge(String transGroup, Integer productQuantity, Integer distance) {
        Map<String, List<Transport>> transportChargesMap = transportService.transportListByTransGroup(transGroup).stream().collect(Collectors.groupingBy(Transport::getTransGroup));
        return transportChargeCalculationMethod(transportChargesMap.getOrDefault(transGroup, new ArrayList<>()), productQuantity, distance);
    }

    @Override
    public UserCart map(CartRequest cartRequest, UserCart userCart) throws UnitedSuppliesException {

        LOG.info("Update Cart Request to Cart Entity");
        if (StringUtils.isNotEmpty(cartRequest.getProductVendorId())) {
            userCart.setProdVendorId(Long.valueOf(cartRequest.getProductVendorId()));
        }
        String destinationPincode = addressService.findDefaultAddressByUserAndCategory(userCart.getUserId(), AddressCategory.DELIVERY_ADDRESS.getStatus()).get().getPostalCode();
        String sourcePincode = productVendorService.findProductVendor(Long.valueOf(cartRequest.getProductVendorId())).getPinCode();
        Integer toAddressDistance = getDistanceByPincodeRange(sourcePincode, destinationPincode);
        userCart.setQuantity(Integer.valueOf(cartRequest.getQuantity()));
        userCart.setUnitId(stringToLong(cartRequest.getUnitId()));
        userCart.setTransCharges(findTransportCharge(cartRequest.getTransGroup(), Integer.valueOf(cartRequest.getQuantity()), toAddressDistance));
        userCart.setUpdatedBy(getCurrentUser());
        userCart.setUpdatedDate(getInstant());
        return userCart;
    }
}
