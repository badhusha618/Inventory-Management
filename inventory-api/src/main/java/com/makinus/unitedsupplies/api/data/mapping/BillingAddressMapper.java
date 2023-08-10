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

import com.makinus.unitedsupplies.api.controller.request.BillingAddressRequest;
import com.makinus.unitedsupplies.common.data.entity.Address;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.unitedsupplies.common.data.reftype.AddressType;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static com.makinus.unitedsupplies.common.data.reftype.AddressCategory.BILLING_ADDRESS;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by sabique
 */
@Component
@Qualifier("BillingAddressMapper")
public class BillingAddressMapper implements EntityWithExtraValueMapper<BillingAddressRequest, Long, Address>, EntityUpdateMapper<BillingAddressRequest, Address> {

    private final Logger LOG = LogManager.getLogger(BillingAddressMapper.class);

    @Override
    public Address mapExtra(BillingAddressRequest billingAddressRequest, Long userId) {
        LOG.info("Map Billing Address Request to Address Entity");
        Address address = new Address();
        address.setUserId(userId);
        address.setName(billingAddressRequest.getFullName());
        address.setMobile(billingAddressRequest.getMobile());
        address.setAddress(billingAddressRequest.getAddress());
        address.setPostalCode(billingAddressRequest.getPostalCode());
        address.setCity(billingAddressRequest.getCity());
        address.setCategory(BILLING_ADDRESS.getStatus());
        address.setType(AddressType.statusMatch(billingAddressRequest.getType()).getStatus());
        if (AddressType.FIRM_OR_COMPANY.getStatus().equals(billingAddressRequest.getType())) {
            address.setGstNo(billingAddressRequest.getGstNo());
        }
        address.setCreatedBy(getCurrentUser());
        address.setCreatedDate(getInstant());
        address.setUpdatedBy(getCurrentUser());
        address.setUpdatedDate(getInstant());
        address.setDeleted(YNStatus.NO.getStatus());
        return address;
    }

    @Override
    public Address map(BillingAddressRequest billingAddressRequest, Address address) {
        LOG.info("Map Billing Address request to Updated Address Entity");
        address.setName(billingAddressRequest.getFullName());
        address.setMobile(billingAddressRequest.getMobile());
        address.setAddress(billingAddressRequest.getAddress());
        address.setPostalCode(billingAddressRequest.getPostalCode());
        address.setCity(billingAddressRequest.getCity());
        address.setType(AddressType.statusMatch(billingAddressRequest.getType()).getStatus());
        address.setGstNo(AddressType.FIRM_OR_COMPANY.getStatus().equals(billingAddressRequest.getType()) ? billingAddressRequest.getGstNo() : StringUtils.EMPTY);
        address.setUpdatedBy(getCurrentUser());
        address.setUpdatedDate(getInstant());
        return address;
    }
}
