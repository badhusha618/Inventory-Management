/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.controller.admin;

import com.makinus.unitedsupplies.admin.data.forms.MaterialUserForm;
import com.makinus.unitedsupplies.admin.data.mapping.AdminUserMapper;
import com.makinus.unitedsupplies.admin.data.service.user.AdminUserService;
import com.makinus.unitedsupplies.admin.encrypt.MakinusCryptor;
import com.makinus.unitedsupplies.common.data.entity.AdminUser;
import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.reftype.UserRole;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import com.makinus.unitedsupplies.common.data.service.order.OrderService;
import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author abuabdul
 */
@Controller
public class UserController extends MainController {

    private final Logger LOG = LogManager.getLogger(this.getClass());

    private static final String USERS_PAGE = "dashboard/users/user-management";
    private static final String USER_SETTINGS_PAGE = "dashboard/users/user-settings";
    private static final String USER_PWD_PAGE = "dashboard/users/password-change";
    private static final String VIEW_USER_PROFILE = "dashboard/users/user-profile";

    @Value("${umat.default.password}")
    private String defaultPassword;

    @Autowired
    private AdminUserService materialUserService;

    @Autowired
    private MakinusCryptor makinusCryptor;

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("AdminUserMapper")
    private AdminUserMapper adminUserMapper;

    @ModelAttribute("newOrderList")
    public List<Order> newOrderList() {
        return orderService.newOrderList();
    }

    @ModelAttribute("prodOrderCountMap")
    public Map<Long, Integer> prodOrderCountMap() {
        return orderService.orderProdOrderCount().stream().collect(Collectors.toMap(Tuple::getA, Tuple::getB));
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping(value = "/users.mk")
    public String userManagement(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open User Management page - {}", this.getClass().getSimpleName());
        model.addAttribute("newUserForm", new MaterialUserForm());
        model.addAttribute("materialUserList", materialUserService.materialUserList());
        return USERS_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/add/user.mk")
    public String addNewUser(@ModelAttribute("newUserForm") MaterialUserForm newUserForm, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Add New User on Dashboard - {}", this.getClass().getSimpleName());
        AdminUser savedUser = materialUserService.addOrUpdateMaterialUser(adminUserMapper.map(newUserForm));
        redirectAttrs.addFlashAttribute("userName", newUserForm.getUsername());
        LOG.debug("UnitedSupplies User Details {}", savedUser.toString());
        return "redirect:/users.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/user/available.mk", produces = "application/json")
    @ResponseBody
    public Boolean isExistingUser(HttpServletRequest request) throws UnitedSuppliesException {
        LOG.info("Checking if the user exists - {}", this.getClass().getSimpleName());
        boolean isUserAvailable = materialUserService.isMaterialUserAvailable(request.getParameter("username").trim());
        LOG.info("User is available? {}", isUserAvailable);
        return !isUserAvailable;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/user/{id}/remove.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> removeUser(HttpServletRequest request, @PathVariable String id) {
        LOG.info("Remove user from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            AdminUser removeUser = materialUserService.removeUser(Long.valueOf(id));
            LOG.info("User is removed? {}", (removeUser != null && removeUser.getDeleted().equalsIgnoreCase(YNStatus.YES.getStatus())));
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException usm) {
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = "/user/pwd.mk")
    public String changePassword(ModelMap model) {
        LOG.info("Change User Password page - {}", this.getClass().getSimpleName());
        model.addAttribute("changePwdForm", new MaterialUserForm());
        return USER_PWD_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/user/update/pwd.mk")
    public String updatePassword(@ModelAttribute("changePwdForm") MaterialUserForm changeUserForm, ModelMap model, Principal loggedInUser, RedirectAttributes redirectAttrs) throws UnitedSuppliesException {
        LOG.info("Update User Current Password - {}", this.getClass().getSimpleName());
        AdminUser activeUser = materialUserService.findActiveUser(loggedInUser.getName());
        if (!makinusCryptor.matches(changeUserForm.getCurrentPassword(), activeUser.getPassword())) {
            LOG.warn("User Current Password does not match");
            model.addAttribute("changePasswordError", Boolean.TRUE);
            return USER_PWD_PAGE;
        }
        materialUserService.addOrUpdateMaterialUser(adminUserMapper.map(changeUserForm, activeUser));
        redirectAttrs.addFlashAttribute("userName", loggedInUser.getName());
        return "redirect:/user/pwd.mk";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping(value = "/user/settings.mk")
    public String userSettings(ModelMap model) throws UnitedSuppliesException {
        LOG.info("Open User Settings page - {}", this.getClass().getSimpleName());
        List<MaterialUserForm> materialUserList =
                materialUserService.materialUserList().stream()
                        .map(mat -> {
                            MaterialUserForm userForm = new MaterialUserForm();
                            userForm.setUserID(String.valueOf(mat.getId()));
                            userForm.setUsername(mat.getUsername());
                            UserRole userRole = UserRole.statusMatch(mat.getRole());
                            userForm.setRole(mat.getRole());
                            userForm.setStatus(userRole.getDisplay());
                            userForm.setStatusValue(userRole.getStatus());
                            return userForm;
                        }).collect(Collectors.toList());
        model.addAttribute("materialUserList", materialUserList);
        return USER_SETTINGS_PAGE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/user/{id}/pwd/reset.mk", produces = "application/json")
    @ResponseBody
    public Map<String, Boolean> resetUserPassword(@PathVariable String id) {
        LOG.info("Reset user password from dashboard - {}", this.getClass().getSimpleName());
        Map<String, Boolean> map = new HashMap<>();
        try {
            AdminUser resetUser = materialUserService.resetPassword(Long.valueOf(id), makinusCryptor.hashpw(defaultPassword));
            LOG.info("User is reset? {}", (resetUser != null));
            map.put("valid", Boolean.TRUE);
        } catch (UnitedSuppliesException usm) {
            LOG.info("Exception occurred at resetUserPassword {}", usm.getMessage());
            map.put("valid", Boolean.FALSE);
        }
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @PostMapping(value = "/user/role/change.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> changeUserRole(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Action on Admin Panel Users from dashboard - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("msg", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("msg", "cannot be empty");
            return map;
        }
        UserRole role = UserRole.statusMatch(value);
        materialUserService.updateMaterialUserRole(Long.valueOf(pk), role);
        map.put("status", "success");
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_ADMIN')")
    @GetMapping(value = "/user/role/list.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> loadUserRoleList(HttpServletResponse response) {
        LOG.info("Load User Role List from dashboard - {}", this.getClass().getSimpleName());
        Map<String, String> map = new HashMap<>();
        map.put(UserRole.ADMIN.getStatus(), UserRole.ADMIN.getDisplay());
        map.put(UserRole.MANAGER.getStatus(), UserRole.MANAGER.getDisplay());
        response.setStatus(HttpServletResponse.SC_OK);
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN')")
    @GetMapping(value = {"/user/{username}/profile/view.mk"})
    public String viewUserProfile(@PathVariable("username") String username, ModelMap model) throws UnitedSuppliesException {
        LOG.info("View User Profile - UserController - {}", this.getClass().getSimpleName());
        model.addAttribute("userProfile", materialUserService.findActiveUser(username));
        model.addAttribute("roleMap", Arrays.stream(UserRole.values()).collect(Collectors.toMap(UserRole::getStatus, UserRole::getDisplay)));
        return VIEW_USER_PROFILE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @GetMapping(value = {"/user/profile/view.mk"})
    public String viewUserProfile(Principal loggedInUser, ModelMap model) throws UnitedSuppliesException {
        LOG.info("View User Profile - UserController - {}", this.getClass().getSimpleName());
        model.addAttribute("userProfile", materialUserService.findActiveUser(loggedInUser.getName()));
        model.addAttribute("roleMap", Arrays.stream(UserRole.values()).collect(Collectors.toMap(UserRole::getStatus, UserRole::getDisplay)));
        return VIEW_USER_PROFILE;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/user/update/name.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> updateFullName(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Update Full Name - UserController - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("body", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("body", "cannot be empty");
            return map;
        }
        materialUserService.updateFullName(Long.valueOf(pk), value);
        map.put("status", "success");
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/user/update/mobile.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> updateMobile(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Update Mobile - UserController - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("body", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("body", "cannot be empty");
            return map;
        }
        materialUserService.updateMobile(Long.valueOf(pk), value);
        map.put("status", "success");
        return map;
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN', 'ROLE_ADMIN', 'ROLE_MANAGER')")
    @PostMapping(value = "/user/update/email.mk", produces = "application/json")
    @ResponseBody
    public Map<String, String> updateEmail(@RequestParam String pk, @RequestParam String value, HttpServletResponse response) throws UnitedSuppliesException {
        LOG.info("Update Email - UserController - {}", this.getClass().getSimpleName());
        response.setStatus(HttpServletResponse.SC_OK);
        Map<String, String> map = new HashMap<>();
        if (isEmpty(pk)) {
            map.put("status", "error");
            map.put("body", "cannot update");
            return map;
        }
        if (isEmpty(value)) {
            map.put("status", "error");
            map.put("body", "cannot be empty");
            return map;
        }
        materialUserService.updateEmail(Long.valueOf(pk), value);
        map.put("status", "success");
        return map;
    }
}
