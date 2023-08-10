/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.db.config;

import com.makinus.unitedsupplies.db.flyway.UnitedSuppliesDataCleanMigration;
import com.makinus.unitedsupplies.db.flyway.UnitedSuppliesDataMigration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/** @author abuabdul */
@Configuration
public class UnitedSuppliesDBConfig {

  @Bean
  @Profile("dev")
  public FlywayMigrationStrategy cleanMigrationStrategy() {
    return new UnitedSuppliesDataCleanMigration();
  }

  @Bean
  @Profile("staging")
  public FlywayMigrationStrategy stagingMigrationStrategy() {
    return new UnitedSuppliesDataMigration();
  }

  @Bean
  @Profile("prod")
  public FlywayMigrationStrategy migrationStrategy() {
    return new UnitedSuppliesDataMigration();
  }
}
