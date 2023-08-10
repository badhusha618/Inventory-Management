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

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.ForwardLogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;
import org.springframework.session.web.http.HttpSessionIdResolver;

/** Created by Bad_sha */
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private UserDetailsService userDetailsService;

  @Bean
  public HttpSessionIdResolver httpSessionIdResolver() {
    return HeaderHttpSessionIdResolver.xAuthToken();
  }

  @Override
  public void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf().disable();

    http.cors()
        .and()
        .authorizeRequests()
        .antMatchers("/user/**")
        .hasAnyRole("USER")
        .antMatchers("/**")
        .permitAll()
        .anyRequest()
        .authenticated();

    http.formLogin()
        .loginProcessingUrl("/auth/login")
        .usernameParameter("mobile")
        .passwordParameter("password")
        .successHandler(authenticationSuccessHandler())
        .failureHandler(
            (req, resp, auth) ->
                resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, auth.getMessage()));

    http.logout()
        .logoutUrl("/auth/logout")
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .logoutSuccessHandler(logoutSuccessHandler());

    http.exceptionHandling()
        .accessDeniedHandler(
            (req, res, auth) ->
                res.sendError(HttpServletResponse.SC_UNAUTHORIZED, auth.getMessage()))
        .authenticationEntryPoint(
            (req, res, auth) ->
                res.sendError(
                    HttpServletResponse.SC_UNAUTHORIZED, "Session Expired, Please login again"));
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    // web.ignoring().antMatchers("/auth/success", "/sign-up", "/actuator/**", "/v2/api-docs",
    // "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/resources/**");
  }

  @Bean
  public AuthenticationSuccessHandler authenticationSuccessHandler() {
    return new ForwardAuthenticationSuccessHandler("/auth/success") {
      @Override
      public void onAuthenticationSuccess(
          HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException {
        super.onAuthenticationSuccess(request, response, authentication);
      }
    };
  }

  @Bean
  public LogoutSuccessHandler logoutSuccessHandler() {
    return new ForwardLogoutSuccessHandler("/auth/clear") {
      @Override
      public void onLogoutSuccess(
          HttpServletRequest request, HttpServletResponse response, Authentication authentication)
          throws IOException, ServletException {
        super.onLogoutSuccess(request, response, authentication);
      }
    };
  }
}
