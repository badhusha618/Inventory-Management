/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author abuabdul
 */
@Controller
public class LoginController {

    private final Logger LOG = LogManager.getLogger(LoginController.class);

    private static final String LOG_IN_PAGE = "login/login";

    @GetMapping(value = {"/login.mk"})
    public String index() {
        LOG.info("Open login page - {}", this.getClass().getSimpleName());
        return LOG_IN_PAGE;
    }
}
