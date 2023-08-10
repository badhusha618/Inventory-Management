/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package inventory.db.config;

import inventory.db.flyway.InventoryDataCleanMigration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Bad_sha
 */
@Configuration
public class InventoryDBConfig {

  @Bean
  @Profile("dev")
  public FlywayMigrationStrategy cleanMigrationStrategy() {
    return new InventoryDataCleanMigration();
  }


}
