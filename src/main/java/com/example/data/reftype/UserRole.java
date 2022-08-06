/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.data.reftype;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by BAD_SHA
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum UserRole {

    SUPER_ADMIN("S", "Super Administrator", "ROLE_SUPER_ADMIN"),
    ADMIN("A", "Administrator", "ROLE_ADMIN"),
    EDITOR("E", "Editor", "ROLE_EDITOR"),
    RESTRICTED("X", "Blocked User", "ROLE_RESTRICTED");

    private String role;
    private String display;
    private String roleName;

    UserRole(String role, String display, String roleName) {
        this.role = role;
        this.display = display;
        this.roleName = roleName;
    }

    public String getRole() {
        return role;
    }

    public String getDisplay() {
        return display;
    }

    public String getRoleName() {
        return roleName;
    }

    public static UserRole statusMatch(String role) {
        for (UserRole userRole : UserRole.values()) {
            if (userRole.getRole().equalsIgnoreCase(role)) {
                return userRole;
            }
        }
        return EDITOR;
    }
}
