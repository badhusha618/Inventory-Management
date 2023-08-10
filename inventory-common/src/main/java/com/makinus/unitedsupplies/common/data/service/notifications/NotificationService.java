/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.notifications;

import com.makinus.unitedsupplies.common.data.entity.Notifications;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface NotificationService {

    Notifications saveNotifications(Notifications notifications);

    List<Notifications> notificationsList();
}
