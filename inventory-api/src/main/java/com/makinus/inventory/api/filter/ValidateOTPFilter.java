package com.makinus.inventory.api.filter;

import static com.makinus.unitedsupplies.common.data.reftype.OtpType.statusMatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makinus.inventory.api.controller.request.ValidateOtpRequest;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.data.service.userotp.UserOtpService;
import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;

@Component
public class ValidateOTPFilter extends OncePerRequestFilter {

  @Autowired private UserDetailsService userDetailsService;

  @Autowired private UserOtpService userOtpService;

  @Autowired private MobileUserService mobileUserService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain chain)
      throws ServletException, IOException {
    if (request.getRequestURI().contains("/auth/otp/validate")) {
      ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
      ValidateOtpRequest otpRequest =
          new ObjectMapper().readValue(requestWrapper.getInputStream(), ValidateOtpRequest.class);
      Optional<User> userByPhone = mobileUserService.findUserByPhone(otpRequest.getMobile());
      if (!userByPhone.isPresent()) {
        requestWrapper.setAttribute("userNotFound", Boolean.TRUE);
        chain.doFilter(requestWrapper, response);
        return;
      }
      Optional<UserOTP> userOTPOptional =
          userOtpService.findLatestOtpByMobileAndByOtpType(
              otpRequest.getMobile(), statusMatch(otpRequest.getOtpType()).getStatus());
      if (!userOTPOptional.isPresent()) {
        requestWrapper.setAttribute("otpNotFound", Boolean.TRUE);
        chain.doFilter(requestWrapper, response);
        return;
      }
      UserOTP userOTP = userOTPOptional.get();
      if (!userOTP.getOtp().equalsIgnoreCase(otpRequest.getOtp())) {
        requestWrapper.setAttribute("incorrectOTP", Boolean.TRUE);
        chain.doFilter(requestWrapper, response);
        return;
      }
      UserDetails userDetails = this.userDetailsService.loadUserByUsername(otpRequest.getMobile());
      UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      usernamePasswordAuthenticationToken.setDetails(
          new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
      SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
      chain.doFilter(requestWrapper, response);
      return;
    }
    chain.doFilter(request, response);
  }
}
