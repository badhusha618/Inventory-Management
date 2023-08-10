/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.config;

import static com.makinus.unitedsupplies.common.utils.SecurityConstants.HEADER_X_AUTH_TOKEN;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${us.app.origins}")
  private String origins;

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedOrigins(origins)
        .allowCredentials(Boolean.TRUE)
        .allowedMethods("POST", "GET", "PUT", "DELETE")
        .exposedHeaders(HEADER_X_AUTH_TOKEN);
  }
}
