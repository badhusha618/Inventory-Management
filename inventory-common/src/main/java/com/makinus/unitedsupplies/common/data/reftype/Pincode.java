/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.reftype;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Bad_sha
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Pincode {
    TIRUNELVELI_627001(1, "627001", "Tirunelveli"),
    TIRUNELVELI_627002(2, "627002", "Tirunelveli"),
    TIRUNELVELI_627003(3, "627003", "Tirunelveli"),
    TIRUNELVELI_627004(4, "627004", "Tirunelveli"),
    TIRUNELVELI_627005(5, "627005", "Tirunelveli"),
    TIRUNELVELI_627007(7, "627007", "Tirunelveli"),
    TIRUNELVELI_627008(8, "627008", "Tirunelveli"),
    TIRUNELVELI_627009(9, "627009", "Tirunelveli"),
    TIRUNELVELI_627010(10, "627010", "Tirunelveli"),
    TIRUNELVELI_627011(11, "627011", "Tirunelveli"),
    TIRUNELVELI_627012(12, "627012", "Tirunelveli"),
    TIRUNELVELI_627356(356, "627356", "Tirunelveli");

    private int code;
    private String pinCode;
    private String city;

    Pincode(int code, String pinCode, String city) {
        this.code = code;
        this.pinCode = pinCode;
        this.city = city;
    }

    public int getCode() {
        return code;
    }

    @JsonIgnore
    public String getCodeStr() {
        return String.valueOf(code);
    }

    public String getPinCode() {
        return pinCode;
    }

    public String getCity() {
        return city;
    }

    public static Pincode codeMatch(int code) {
        for (Pincode state : Pincode.values()) {
            if (state.getCode() == code) {
                return state;
            }
        }
        return TIRUNELVELI_627001;
    }
}
