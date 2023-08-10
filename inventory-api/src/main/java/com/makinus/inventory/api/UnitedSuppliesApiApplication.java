/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api;

import static com.makinus.inventory.api.UnitedSuppliesApiApplication.COMMON_BEANS;
import static com.makinus.inventory.api.UnitedSuppliesApiApplication.PACKAGE_NAME;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Bad_sha
 */
@SpringBootApplication
@EntityScan(PACKAGE_NAME)
@ComponentScan(COMMON_BEANS)
@EnableJpaRepositories(COMMON_BEANS)
@EnableSwagger2
public class UnitedSuppliesApiApplication extends SpringBootServletInitializer {

  public static final String PACKAGE_NAME = "com.makinus.unitedsupplies.common.data.entity";
  public static final String COMMON_BEANS = "com.makinus.unitedsupplies";

  public static void main(String[] args) {
    SpringApplication.run(UnitedSuppliesApiApplication.class, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
    return builder.sources(UnitedSuppliesApiApplication.class);
  }
}
