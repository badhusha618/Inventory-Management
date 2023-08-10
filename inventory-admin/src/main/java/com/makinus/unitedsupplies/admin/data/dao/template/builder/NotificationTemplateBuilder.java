/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.dao.template.builder;

import com.makinus.unitedsupplies.admin.data.forms.NotificationForm;
import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

/**
 * Created by hussain
 */
public class NotificationTemplateBuilder {

    private static String templateString = StringUtils.EMPTY;

    private NotificationTemplateBuilder() {
    }

    public static NotificationTemplateBuilder notificationTemplateBuilder() {
        return new NotificationTemplateBuilder();
    }

    public NotificationTemplateBuilder orderPlaced(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder paymentPending(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder paymentFailed(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder inProgress(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder shipped(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo(), notificationForm.getDeliveryDate());
        }
        return this;
    }

    public NotificationTemplateBuilder inTransit(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder delivered(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public NotificationTemplateBuilder cancelled(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo(), notificationForm.getReason());
        }
        return this;
    }

    public NotificationTemplateBuilder refund(String template, NotificationForm notificationForm) {
        if (StringUtils.isNotEmpty(template)) {
            templateString = MessageFormat.format(template, notificationForm.getCustomerName(), notificationForm.getRefund(), notificationForm.getItemFirstName(), notificationForm.getItemLastName(), notificationForm.getOrderNo());
        }
        return this;
    }

    public String build() {
        return templateString;
    }

}
