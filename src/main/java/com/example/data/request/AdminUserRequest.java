/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.example.data.request;

import lombok.*;

/**
 * Created by BAD_SHA
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminUserRequest {

    private String fullName;
    private String mobile;
    private String password;
    private String role;
}