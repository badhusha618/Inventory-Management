/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.db.flyway;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfo;
import org.flywaydb.core.api.MigrationInfoService;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;

/** Created by abuabdul */
public class UnitedSuppliesDataMigration implements FlywayMigrationStrategy {

  private final Logger LOG = LogManager.getLogger(UnitedSuppliesDataMigration.class);

  @Override
  public void migrate(Flyway flyway) {
    flyway.repair();
    flyway.migrate();
    flyway.validate();
    this.prettyDisplay(flyway.info());
  }

  private void prettyDisplay(final MigrationInfoService migrationInfo) {
    LOG.debug("USM Flyway - Pretty Display");
    MigrationInfo[] all = migrationInfo.all();
    if (all.length != 0) {
      for (MigrationInfo info : all) {
        LOG.info(
            "Version: {} Description: {} Type: {} Installed By: {} Script Name: {} Status: {}",
            info.getVersion(),
            info.getDescription(),
            info.getType(),
            info.getInstalledBy(),
            info.getScript(),
            info.getState());
      }
    }
  }
}
