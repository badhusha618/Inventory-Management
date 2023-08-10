/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static com.makinus.unitedsupplies.admin.InventoryAdminApplication.COMMON_BEANS;
import static com.makinus.unitedsupplies.admin.InventoryAdminApplication.PACKAGE_NAME;

/**
 * @author Bad_sha
 */
@SpringBootApplication
@EntityScan(PACKAGE_NAME)
@EnableSwagger2
@ComponentScan(COMMON_BEANS)
@EnableJpaRepositories(COMMON_BEANS)
public class InventoryAdminApplication extends SpringBootServletInitializer {

    public static final String PACKAGE_NAME = "com.makinus.Inventory.common.data.entity";
    public static final String COMMON_BEANS = "com.makinus.Inventory";

    public static void main(String[] args) {
        SpringApplication.run(InventoryAdminApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(InventoryAdminApplication.class);
    }
}
