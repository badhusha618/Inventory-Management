/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makinus.unitedsupplies.api.attribs.Payload;
import com.makinus.unitedsupplies.api.config.SecurityConfig;
import com.makinus.unitedsupplies.api.controller.request.LoginOtpRequest;
import com.makinus.unitedsupplies.api.controller.request.LoginRequest;
import com.makinus.unitedsupplies.api.controller.request.ProfileRequest;
import com.makinus.unitedsupplies.api.controller.request.ValidateOtpRequest;
import com.makinus.unitedsupplies.api.data.mapping.MobileUserMapper;
import com.makinus.unitedsupplies.api.data.mapping.UserOtpMapper;
import com.makinus.unitedsupplies.common.data.entity.Base;
import com.makinus.unitedsupplies.common.data.entity.User;
import com.makinus.unitedsupplies.common.data.entity.UserOTP;
import com.makinus.unitedsupplies.common.data.reftype.ApiResponseStatus;
import com.makinus.unitedsupplies.common.data.reftype.OtpType;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.user.MobileUserService;
import com.makinus.unitedsupplies.common.data.service.userotp.UserOtpService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import com.makinus.unitedsupplies.common.sms.SMSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.ContentCachingRequestWrapper;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.data.reftype.OtpType.statusMatch;
import static com.makinus.unitedsupplies.common.data.reftype.YNStatus.YES;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

/**
 * @author abuabdul
 */
@RestController
@RequestMapping(value = "/auth")
@Api(value = "Authentication API Controller")
public class AuthController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    @Value("${us.app.base.url}")
    protected String baseUrl;

    @Value("${sms.api.message.template.otp.login}")
    private String smsMessageTemplateOtpLogin;

    @Value("${sms.api.message.template.otp.forget.password}")
    private String smsMessageTemplateOtpForgetPassword;

    @Autowired
    private MobileUserService mobileUserService;

    @Autowired
    private UserOtpService userOtpService;

    @Autowired
    private SMSService smsService;

    @Autowired
    private MobileUserMapper mobileUserMapper;

    @Autowired
    private UserOtpMapper userOtpMapper;

    @ApiOperation("Fake Login URL for Users")
    @PostMapping(value = "/login", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<User> fakeLogin(@ApiParam("LoginRequest") LoginRequest loginRequest) {
        throw new IllegalStateException(
                "This method shouldn't be called. It's implemented by Spring Security. See '"
                        + SecurityConfig.class.getName()
                        + "' class for details.");
    }

    @ApiOperation("Fake Logout URL for Users")
    @PostMapping("/logout")
    public void fakeLogout() {
        throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security. See '" + SecurityConfig.class.getName() + "' class for details.");
    }

    @ApiOperation(value = "Authentication Success Forward URL", hidden = true)
    @PostMapping("/success")
    public ResponseEntity<User> getAuthToken() throws UnitedSuppliesException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            User user = mobileUserService.findByMobile(authentication.getName());
            if (user == null) {
                return new ResponseEntity<>(new User(ApiResponseStatus.NOT_FOUND.getStatus()), HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ApiOperation(value = "Logout Success Forward URL", hidden = true)
    @PostMapping("/clear")
    public ResponseEntity<Base> clearLogout() {
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus()), HttpStatus.OK);
    }

    @ApiOperation("To sign up in United Supplies")
    @PostMapping("/sign-up")
    public ResponseEntity<User> signUp(@ApiParam("Sign Up Request") @RequestBody ProfileRequest request) {
        if (mobileUserService.findUserByPhone(request.getMobile()).isPresent()) {
            return new ResponseEntity<>(new User(ApiResponseStatus.EXISTS.getStatus()), HttpStatus.NOT_FOUND);
        }
        User savedUser = mobileUserService.saveMobileUser(mobileUserMapper.map(request));
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }

    @ApiOperation("Request OTP to a user")
    @PostMapping("/otp/invoke")
    public ResponseEntity<Base> invokeOTP(@ApiParam("Invoke OTP") @RequestBody LoginOtpRequest loginOtpRequest) throws UnitedSuppliesException {
        LOG.info("Request OTP Request - {}", this.getClass().getSimpleName());
        Optional<User> optionalUser = mobileUserService.findUserByPhone(loginOtpRequest.getMobile());
        if (!optionalUser.isPresent()) {
            LOG.info("User is not found with the mobile number {}", loginOtpRequest.getMobile());
            return new ResponseEntity<>(new Base(ApiResponseStatus.NOT_FOUND.getStatus()), HttpStatus.NOT_FOUND);
        }
        Optional<UserOTP> optionalUserOTP = userOtpService.findLatestOtpByMobileAndByOtpType(loginOtpRequest.getMobile(), loginOtpRequest.getOtpType());
        if (optionalUserOTP.isPresent() && otpAlreadyInvoked(optionalUser.get(), optionalUserOTP.get())) {
            return new ResponseEntity<>(new Base(ApiResponseStatus.TIME_IN.getStatus()), HttpStatus.NOT_FOUND);
        }
        Payload payload = getPayload();
        Tuple<String, String> otpTuple = new Tuple<>();
        otpTuple.setA(payload.getLoginOTP());
        otpTuple.setB(statusMatch(loginOtpRequest.getOtpType()).getStatus());
        userOtpService.saveUserOtp(userOtpMapper.mapExtra(optionalUser.get(), otpTuple));
        mobileUserService.otpInvoked(optionalUser.get().getId());
        if (OtpType.FORGET_PASSWORD.getStatus().equals(loginOtpRequest.getOtpType())) {
            smsService.sendOTP(optionalUser.get().getMobile(), MessageFormat.format(smsMessageTemplateOtpForgetPassword, payload.getLoginOTP()));
        }
        if (OtpType.LOGIN_VIA_OTP.getStatus().equals(loginOtpRequest.getOtpType())) {
            smsService.sendOTP(optionalUser.get().getMobile(), MessageFormat.format(smsMessageTemplateOtpLogin, payload.getLoginOTP()));
        }
        return new ResponseEntity<>(new Base(ApiResponseStatus.SUCCESS.getStatus(), payload.getLoginOTP()), HttpStatus.OK);
    }

    @ApiOperation("Validate One Time Password For Login")
    @PostMapping("/otp/validate")
    public ResponseEntity<User> validateLoginOtp(@ApiParam("Validate OTP") @RequestBody(required = false) ValidateOtpRequest validateOtpRequest, HttpServletRequest request) throws IOException, UnitedSuppliesException {
        LOG.info("Validate OTP Request - AuthController - {}", this.getClass().getSimpleName());
        ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) request;
        validateOtpRequest = new ObjectMapper().readValue(requestWrapper.getContentAsByteArray(), ValidateOtpRequest.class);
        Boolean userNotFound = (Boolean) request.getAttribute("userNotFound");
        if (userNotFound != null && userNotFound) {
            LOG.info("User does not exist {}", validateOtpRequest.getMobile());
            return new ResponseEntity<>(new User(ApiResponseStatus.NOT_FOUND.getStatus()), HttpStatus.NOT_FOUND);
        }
        Boolean otpNotFound = (Boolean) request.getAttribute("otpNotFound");
        if (otpNotFound != null && otpNotFound) {
            LOG.info("OTP has not been generated {}", validateOtpRequest.getOtp());
            return new ResponseEntity<>(new User(ApiResponseStatus.NOT_FOUND.getStatus()), HttpStatus.NOT_FOUND);
        }
        Boolean incorrectOTP = (Boolean) request.getAttribute("incorrectOTP");
        if (incorrectOTP != null && incorrectOTP) {
            LOG.info("OTP is incorrect {}", validateOtpRequest.getOtp());
            return new ResponseEntity<>(new User(ApiResponseStatus.INCORRECT_PWD.getStatus()), HttpStatus.NOT_FOUND);
        }
        userOtpService.removeUserOTP(validateOtpRequest.getMobile(), validateOtpRequest.getOtpType());
        User user = mobileUserService.findByMobile(validateOtpRequest.getMobile());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    private Payload getPayload() {
        Payload payload = new Payload();
        payload.setLoginOTP(randomNumeric(4));
        return payload;
    }

    private boolean otpAlreadyInvoked(User user, UserOTP userOTP) {
        int tenMinutes = 10 * 60 * 1000;
        long expiryMinutes = System.currentTimeMillis() - tenMinutes;
        long updatedTime = userOTP.getCreatedDate().getTime();
        if (updatedTime > expiryMinutes && user.getOtpInvoked().equalsIgnoreCase(YES.getStatus())) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
