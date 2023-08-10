/* *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 **/
package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.api.controller.response.ComplianceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.makinus.unitedsupplies.common.utils.ApiUtils.*;

/**
 * @author abuabdul
 */
@RestController
@Api(value = "Compliance API Controller")
public class ComplianceController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @ApiOperation("Privacy Policy")
    @GetMapping("/privacy")
    public ResponseEntity<ComplianceResponse> privacy() {
        LOG.info("Privacy Policy request - ComplianceController");
        ComplianceResponse response = new ComplianceResponse();
        response.setDescription(PRIVACY_CONTENT);
        response.setTitle(PRIVACY_TITLE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation("Refund Policy")
    @GetMapping("/refund")
    public ResponseEntity<ComplianceResponse> refund() {
        LOG.info("Refund Policy request - ComplianceController");
        ComplianceResponse refund = new ComplianceResponse();
        refund.setTitle(REFUND_TITLE);
        refund.setDescription(REFUND_CONTENT);
        return new ResponseEntity<>(refund, HttpStatus.OK);
    }

    @ApiOperation("Shipping Policy")
    @GetMapping("/shipping")
    public ResponseEntity<ComplianceResponse> shipping() {
        LOG.info("Shipping Policy request - ComplianceController - {}", this.getClass().getSimpleName());
        ComplianceResponse shipping = new ComplianceResponse();
        shipping.setDescription(SHIPPING_CONTENT);
        shipping.setTitle(SHIPPING_TITLE);
        return new ResponseEntity<>(shipping, HttpStatus.OK);
    }

    @ApiOperation("Terms Of Service")
    @GetMapping("/terms")
    public ResponseEntity<ComplianceResponse> terms() {
        LOG.info("Terms of Service request - ComplianceController - {}", this.getClass().getSimpleName());
        ComplianceResponse terms = new ComplianceResponse();
        terms.setDescription(TERMS_CONTENT);
        terms.setTitle(TERMS_TITLE);
        return new ResponseEntity<>(terms, HttpStatus.OK);
    }
}
