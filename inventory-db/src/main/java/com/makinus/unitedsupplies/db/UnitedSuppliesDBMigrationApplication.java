/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/** Created by abuabdul */
@SpringBootApplication
public class UnitedSuppliesDBMigrationApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(UnitedSuppliesDBMigrationApplication.class, args).close();
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(UnitedSuppliesDBMigrationApplication.class);
  }
}
