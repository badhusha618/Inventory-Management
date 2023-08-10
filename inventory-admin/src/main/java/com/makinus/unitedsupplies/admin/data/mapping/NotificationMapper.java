/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.NotificationForm;
import com.makinus.unitedsupplies.common.data.entity.Notifications;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.reftype.NotificationOrderStatus;
import com.makinus.unitedsupplies.common.data.reftype.NotificationStatus;
import com.makinus.unitedsupplies.common.data.reftype.NotificationType;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.ParseException;

import static com.makinus.unitedsupplies.common.utils.AppUtils.*;

/**
 * Created by ammar
 */
@Component
@Qualifier("NotificationMapper")
public class NotificationMapper
        implements EntityMapper<NotificationForm, Notifications> {

    private final Logger LOG = LogManager.getLogger(NotificationMapper.class);

    @Override
    public Notifications map(NotificationForm notificationForm) throws UnitedSuppliesException, ParseException {
        LOG.info("Map Notification Form to Notification Entity");
        Notifications notifications = new Notifications();
        notifications.setCustomerName(notificationForm.getCustomerName());
        notifications.setOrderRef(Long.valueOf(notificationForm.getOrderRef()));
        notifications.setOrderNo(notificationForm.getOrderNo());
        notifications.setMobileNo(notificationForm.getMobileNo());
        notifications.setSentDate(getInstant());
        notifications.setSentBy(getCurrentUser());
        notifications.setStatus(NotificationStatus.statusMatch(notificationForm.getMsgStatus()).getStatus());
        notifications.setType(NotificationType.statusMatch(notificationForm.getType()).getStatus());
        notifications.setReason(notificationForm.getReason());
        if(StringUtils.isNotEmpty(notificationForm.getRefund())){
            notifications.setRefund(new BigDecimal(notificationForm.getRefund()));
        }
        notifications.setOrderStatus(NotificationOrderStatus.statusMatch(notificationForm.getStatus()).getStatus());
        if (notificationForm.getDeliveryDate() != null && StringUtils.isNotEmpty(notificationForm.getDeliveryDate())) {
            notifications.setDeliveryDate(utcDateForDDMMYYYY(notificationForm.getDeliveryDate()));
        }
        if (StringUtils.isNotEmpty(notificationForm.getProductId())) {
            notifications.setProductId(Long.valueOf(notificationForm.getProductId()));
        }
        notifications.setProductCode(notificationForm.getProductCode());
        notifications.setProductName(notificationForm.getProductName());
        return notifications;
    }
}
