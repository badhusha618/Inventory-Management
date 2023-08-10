/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.dao.template.builder;

import com.makinus.Inventory.admin.data.forms.NotificationForm;
import com.makinus.Inventory.common.data.reftype.NotificationOrderStatus;
import com.makinus.Inventory.common.data.reftype.NotificationPaymentStatus;
import org.apache.commons.lang3.StringUtils;

import static com.makinus.Inventory.common.data.reftype.NotificationOrderStatus.statusMatch;

/**
 * @author Bad_sha
 */
public class TemplateSelector {

    static String SMS_ORDER_PLACED = "Hi {0}, Thanks for shopping with us! your Order No. {1} is successfully placed and will be confirmed shortly. -United Supplies & Ecommerce";
    static String SMS_PAYMENT_PENDING = "Hi {0}, The payment for your Order No. {1} is still pending and will be confirmed after the payment is received successfully. -United Supplies & Ecommerce";
    static String SMS_PAYMENT_FAILED = "Hi {0}, The payment for your Order No. {1} is failed and we will contact you shortly for alternate payment way. -United Supplies & Ecommerce";
    static String SMS_PROGRESS = "Hi {0}, {1}{2} in the order No. {3} is being processed and will be shipped shortly -United Supplies & Ecommerce";
    static String SMS_SHIPPED = "Hi {0}, {1}{2} in the Order No. {3} has just shipped and will be delivered by {4}. -United Supplies & Ecommerce";
    static String SMS_TRANSIT = "Hi {0}, {1}{2} in the Order No. {3} has been reached nearby your location. You can expect to get it within an hour. -United Supplies & Ecommerce";
    static String SMS_DELIVERED = "Hi {0}, {1}{2} in the order No. {3} has been delivered successfully! Not received it? Immediately mail us to support@Inventory.in -United Supplies & Ecommerce";
    static String SMS_CANCELLED = "Hi {0}, we are unable to process the {1}{2} in the Order No. {3} due to {4} so that the order has been cancelled. Refund process will be initiated shortly and deduction may apply in the same as per our T&C. -United Supplies & Ecommerce";
    static String SMS_REFUND = "Hi {0}, you have been refunded Rs. {1} for order cancellation of {2}{3} in the Order No. {4}. -United Supplies & Ecommerce";

    public static String getTemplate(NotificationForm notificationForm) {
        NotificationOrderStatus notificationOrderStatus = statusMatch(notificationForm.getStatus());
        NotificationPaymentStatus notificationPaymentStatus = NotificationPaymentStatus.statusMatch(notificationForm.getPaymentStatus());
        switch (notificationOrderStatus) {
            case NEW:
                switch (notificationPaymentStatus) {
                    case PENDING:
                        return NotificationTemplateBuilder.notificationTemplateBuilder().paymentPending(SMS_PAYMENT_PENDING, notificationForm).build();
                    case FAILED:
                        return NotificationTemplateBuilder.notificationTemplateBuilder().paymentFailed(SMS_PAYMENT_FAILED, notificationForm).build();
                    default:
                        return NotificationTemplateBuilder.notificationTemplateBuilder().orderPlaced(SMS_ORDER_PLACED, notificationForm).build();
                }
            case IN_PROGRESS:
                return NotificationTemplateBuilder.notificationTemplateBuilder().inProgress(SMS_PROGRESS, notificationForm).build();
            case SHIPPED:
                return NotificationTemplateBuilder.notificationTemplateBuilder().shipped(SMS_SHIPPED, notificationForm).build();
            case IN_TRANSIT:
                return NotificationTemplateBuilder.notificationTemplateBuilder().inTransit(SMS_TRANSIT, notificationForm).build();
            case DELIVERED:
                return NotificationTemplateBuilder.notificationTemplateBuilder().delivered(SMS_DELIVERED, notificationForm).build();
            case CANCELLED:
                return NotificationTemplateBuilder.notificationTemplateBuilder().cancelled(SMS_CANCELLED, notificationForm).build();
            case REFUND:
                return NotificationTemplateBuilder.notificationTemplateBuilder().refund(SMS_REFUND, notificationForm).build();
            default:
                return StringUtils.EMPTY;
        }
    }
}
