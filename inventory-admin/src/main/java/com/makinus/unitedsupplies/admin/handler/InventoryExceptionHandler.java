/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.handler;

import javax.servlet.http.HttpServletRequest;

import com.makinus.inventory.exception.MakinusException;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Bad_sha
 */
@ControllerAdvice
public class InventoryExceptionHandler {

  private final Logger LOG = LogManager.getLogger(InventoryExceptionHandler.class);
  private final String ERROR_VIEW = "error/generic-error";

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @org.springframework.web.bind.annotation.ExceptionHandler(InventoryException.class)
  public ModelAndView handleUSMExceptions(HttpServletRequest req, MakinusException usm) {
    ModelAndView view = new ModelAndView();
    view.setViewName(ERROR_VIEW);
    view.addObject("exception", usm.getMessage());
    view.addObject("responseStatus", HttpStatus.NOT_FOUND.value());
    LOG.info("Exception {}", usm);
    return view;
  }

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
  public ModelAndView handleExceptions(HttpServletRequest req, Exception ex) {
    ModelAndView view = new ModelAndView();
    view.setViewName(ERROR_VIEW);
    view.addObject("exception", ex.getMessage());
    view.addObject("responseStatus", HttpStatus.NOT_FOUND.value());
    LOG.info("Exception {}", ex);
    return view;
  }
}
