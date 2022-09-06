/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.config;

import com.example.flyway.DataCleanMigration;
import com.example.flyway.DataMigration;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Created by BAD_SHA
 */
@Configuration
public class MigrationConfig {

    @Bean
    @Profile("dev")
    public FlywayMigrationStrategy cleanMigrationStrategy() {
        return new DataCleanMigration();
    }

    @Bean
    @Profile("docker")
    public FlywayMigrationStrategy dockerMigrationStrategy() {
        return new DataMigration();
    }

    @Bean
    @Profile("prod")
    public FlywayMigrationStrategy migrationStrategy() {
        return new DataMigration();
    }
}
