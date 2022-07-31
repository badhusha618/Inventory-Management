/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.exception.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Created By BAD_SHA
 */
@Controller
public class BazzarErrorController implements ErrorController {

    private final Logger LOG = LogManager.getLogger(BazzarErrorController.class);

    private final String ERROR_PATH = "/error";
    private final String ERROR_VIEW = "error/generic-error";

    @Override
    public String getErrorPath() {
        return this.ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    public String error(HttpServletRequest request, HttpServletResponse resp, Model model) {
        model.addAttribute("responseStatus", resp.getStatus());
        model.addAttribute("exception", "Page not found in the application. It could be bad request or internal server error");
        return ERROR_VIEW;
    }
}
