/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */

package com.example.utils;


import org.springframework.security.core.context.SecurityContextHolder;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

/**
 * Created by BAD_SHA 31?07/22
 */
public interface AdminUtils {

    String ARAR_INFRA_SERIES = "AICC";
    String PURCHASE_ORDER = "PO";
    String QUOTATION_NUMBER_SERIES = "RFQ";
    String COLLECTIVE_NUMBER_SERIES = "CC";
    String HYPHEN = "-";
    SimpleDateFormat dateSeries = new SimpleDateFormat("ddMMyyyy");
    DecimalFormat amountFormat = new DecimalFormat("#,###.00");
    String SYSTEM_USER = "BAD_SHA";

    static String getCurrentUser() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    static Date getInstant() {
        return Date.from(Instant.now());
    }
}
