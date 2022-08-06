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

import com.example.exception.BazzarException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * @Created By BAD_SHA
 */
@ControllerAdvice
public class BazzarExceptionHandler {

    private final Logger LOG = LogManager.getLogger(BazzarExceptionHandler.class);

    private final String ERROR_VIEW = "error/generic-error";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BazzarException.class)
    public ModelAndView handleUSMExceptions(HttpServletRequest req, BazzarException mak) {
        ModelAndView view = new ModelAndView();
        view.setViewName(ERROR_VIEW);
        view.addObject("exception", mak.getMessage());
        view.addObject("responseStatus", HttpStatus.NOT_FOUND.value());
        LOG.info("Exception {}", mak.getStackTrace());
        LOG.info("Exception Message {}", mak.getMessage());
        return view;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Exception.class)
    public ModelAndView handleExceptions(HttpServletRequest req, Exception ex) {
        ModelAndView view = new ModelAndView();
        view.setViewName(ERROR_VIEW);
        view.addObject("exception", ex.getMessage());
        view.addObject("responseStatus", HttpStatus.NOT_FOUND.value());
        LOG.info("Exception {}", ex.getStackTrace());
        LOG.info("Exception Message {}", ex.getMessage());
        return view;
    }

}