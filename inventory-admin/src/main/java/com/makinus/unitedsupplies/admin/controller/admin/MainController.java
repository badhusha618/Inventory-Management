/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.TimeZone;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

public class MainController {

    private final Logger LOG = LogManager.getLogger(MainController.class);

    @ModelAttribute("zone")
    public TimeZone injectTimeZone(
            TimeZone serverZone,
            @CookieValue(value = "us_user_timezone", required = false) String zoneID) {
        LOG.info("Setting Zone in model attribute - MainController - {}", this.getClass().getSimpleName());
        return isNotEmpty(zoneID) ? TimeZone.getTimeZone(zoneID) : serverZone;
    }
}
