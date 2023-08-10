/* *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 **/

package com.makinus.unitedsupplies.api.controller;

import com.makinus.unitedsupplies.api.controller.response.CityLocation;
import com.makinus.unitedsupplies.api.controller.response.CityLocationResponse;
import com.makinus.unitedsupplies.common.data.reftype.City;
import com.makinus.unitedsupplies.common.data.reftype.Pincode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sabique
 */
@RestController
@RequestMapping(value = "/addresses")
@Api(value = "User API Controller")
public class PincodeController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @PreAuthorize("hasAnyRole('ROLE_USER')")
    @ApiOperation("List of all cities with their pin codes")
    @GetMapping("/city/location")
    public ResponseEntity<CityLocationResponse> listCityLocation() {
        LOG.info("List City Location - {}", this.getClass().getSimpleName());
        List<CityLocation> cityLocationList = Arrays.stream(City.values()).map(c -> cityLocation(c.getDisplay())).collect(Collectors.toList());
        return new ResponseEntity<>(new CityLocationResponse(cityLocationList), HttpStatus.OK);
    }

    private CityLocation cityLocation(String city) {
        CityLocation cityLocation = new CityLocation();
        if (StringUtils.isNotEmpty(city)) {
            cityLocation.setCity(city);
            cityLocation.setPinCodes(Arrays.stream(Pincode.values()).filter(p -> p.getCity().equals(city)).map(Pincode::getPinCode).toArray(String[]::new));
        }
        return cityLocation;
    }
}
