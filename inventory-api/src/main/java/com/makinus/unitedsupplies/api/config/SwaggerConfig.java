/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.config;

import static com.makinus.unitedsupplies.common.utils.SecurityConstants.HEADER_NAME;
import static com.makinus.unitedsupplies.common.utils.SecurityConstants.HEADER_X_AUTH_TOKEN;

import com.google.common.collect.Ordering;
import com.makinus.unitedsupplies.api.controller.AuthController;
import java.util.Collections;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

/** Created by abuabdul */
@Configuration
public class SwaggerConfig {

  @Bean
  public ApiKey apiKey() {
    return new ApiKey(HEADER_X_AUTH_TOKEN, HEADER_X_AUTH_TOKEN, HEADER_NAME);
  }

  @Bean
  public Docket docket() {
    Ordering<Operation> operationOrdering =
        new Ordering<Operation>() {
          @Override
          public int compare(Operation left, Operation right) {
            return left.getPosition() - right.getPosition();
          }
        };

    return new Docket(DocumentationType.SWAGGER_2)
        .securitySchemes(Collections.singletonList(apiKey()))
        .securityContexts(Collections.singletonList(securityContext()))
        .operationOrdering(operationOrdering)
        .select()
        .apis(RequestHandlerSelectors.basePackage(AuthController.class.getPackage().getName()))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo());
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
        .title("UnitedSupplies Application REST API")
        .description("UnitedSupplies Application.")
        .version("1.0.0")
        .contact(
            new Contact("Abubacker Siddik A", "https://www.makinus.com", "contact@makinus.com"))
        .termsOfServiceUrl(null)
        .license(null)
        .build();
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/.*"))
        .build();
  }

  private List<SecurityReference> defaultAuth() {
    final AuthorizationScope authorizationScope =
        new AuthorizationScope("global", "accessEverything");
    final AuthorizationScope[] authorizationScopes = new AuthorizationScope[] {authorizationScope};
    return Collections.singletonList(
        new SecurityReference(HEADER_X_AUTH_TOKEN, authorizationScopes));
  }
}
