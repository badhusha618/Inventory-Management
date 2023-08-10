/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.controller.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author Bad_sha
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ComplianceResponse {

    @ApiModelProperty(notes = "Content Title", example = "Privacy Policy", position = 1)
    private String title;

    @ApiModelProperty(notes = "Content Description", example = "Privacy Policy Content", position = 2)
    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
