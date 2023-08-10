/* *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 **/
package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.api.controller.response.AboutUsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author abuabdul
 */
@RestController
@Api(value = "About Us API Controller")
public class AboutUsController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @ApiOperation("About Us")
    @GetMapping("/about-us")
    public ResponseEntity<AboutUsResponse> aboutUsText() {
        LOG.info("About Us request - {}", this.getClass().getSimpleName());
        AboutUsResponse response = new AboutUsResponse();
        response.setDescription("United Supplies & eCommerce Private Limited provides online services to purchase construction materials " +
                "for site engineers,contractors, independent builders and retailers. We have developed a mobile application in android platform " +
                "to do online purchase at your home and site. We offer all types of materials at the best price. Our products include sand, crushed " +
                "stones, cement, steels, bricks, electrical items and hardwares.");
        response.setVersion("v1.0.0");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
