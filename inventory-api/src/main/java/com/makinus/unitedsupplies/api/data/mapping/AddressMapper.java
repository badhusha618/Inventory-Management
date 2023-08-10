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

import com.makinus.unitedsupplies.api.controller.request.AddressRequest;
import com.makinus.unitedsupplies.common.data.entity.Address;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.City;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.data.reftype.AddressCategory.DELIVERY_ADDRESS;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by sabique
 */
@Component
@Qualifier("AddressMapper")
public class AddressMapper implements EntityWithExtraValueMapper<AddressRequest, Long, Address>, EntityUpdateMapper<AddressRequest, Address> {

    private final Logger LOG = LogManager.getLogger(AddressMapper.class);

    @Override
    public Address mapExtra(AddressRequest addressRequest, Long userId) {
        LOG.info("Map Address Request to Address Entity");
        Address address = new Address();
        address.setUserId(userId);
        address.setName(addressRequest.getFullName());
        address.setMobile(addressRequest.getMobile());
        address.setAddress(addressRequest.getAddress());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCity(City.statusMatch(addressRequest.getCity()).getDisplay());
        address.setCategory(DELIVERY_ADDRESS.getStatus());
        address.setCreatedBy(getCurrentUser());
        address.setCreatedDate(getInstant());
        address.setUpdatedBy(getCurrentUser());
        address.setUpdatedDate(getInstant());
        address.setDeleted(YNStatus.NO.getStatus());
        return address;
    }

    @Override
    public Address map(AddressRequest addressRequest, Address address) {
        LOG.info("Map Address request to Updated Address Entity");
        address.setName(addressRequest.getFullName());
        address.setMobile(addressRequest.getMobile());
        address.setAddress(addressRequest.getAddress());
        address.setPostalCode(addressRequest.getPostalCode());
        address.setCity(City.statusMatch(addressRequest.getCity()).getDisplay());
        address.setUpdatedBy(getCurrentUser());
        address.setUpdatedDate(getInstant());
        return address;
    }
}
