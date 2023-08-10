package com.makinus.unitedsupplies.common.data.service.notifications;

/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */

import com.makinus.unitedsupplies.common.data.dao.NotificationsRepository;
import com.makinus.unitedsupplies.common.data.entity.Notifications;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by ammar
 */
@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final Logger LOG = LogManager.getLogger(NotificationServiceImpl.class);

    private final NotificationsRepository notificationsRepository;

    public NotificationServiceImpl(@Autowired NotificationsRepository notificationsRepository) {
        this.notificationsRepository = notificationsRepository;
    }

    @Override
    public Notifications saveNotifications(Notifications notifications) {
        LOG.info("Add New Notifications in the database");
        Notifications savedNotifications = notificationsRepository.save(notifications);
        LOG.info("Saved New Notifications in the database");
        return savedNotifications;
    }

    @Override
    public List<Notifications> notificationsList() {
        LOG.info("List Notifications from database");
        return notificationsRepository.listAllNotifications();
    }
}

