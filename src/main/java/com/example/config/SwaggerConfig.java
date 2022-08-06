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

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static com.example.utils.SecurityConstants.HEADER_X_AUTH_TOKEN;
import static org.springframework.boot.web.servlet.filter.ApplicationContextHeaderFilter.HEADER_NAME;

/**
 * Created by BAD_SHA
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public ApiKey apiKey() {
        return new ApiKey(HEADER_X_AUTH_TOKEN, HEADER_X_AUTH_TOKEN, HEADER_NAME);
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                .securitySchemes(Collections.singletonList(apiKey()))
                .operationOrdering(Comparator.comparingInt(Operation::getPosition))
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(new SecurityReference(HEADER_X_AUTH_TOKEN, authorizationScopes));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("BAZZAR REST API").description("The BAZZAR Application.").version("1.0.0")
                .contact(new Contact("BAZZAR", "https://www.bazzar.com", null))
                .termsOfServiceUrl(null).license(null).build();
    }
}
