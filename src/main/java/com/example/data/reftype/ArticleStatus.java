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

/**
 * Created by BAD_SHA
 */
public enum ArticleStatus {

    DRAFT("D", "Draft"),
    PUBLISHED("P", "Published");

    private String status;
    private String display;

    ArticleStatus(String status, String display) {
        this.status = status;
        this.display = display;
    }

    public String getStatus() {
        return status;
    }

    public String getDisplay() {
        return display;
    }

    public static ArticleStatus displayMatch(String display) {
        for (ArticleStatus articleStatus : ArticleStatus.values()) {
            if (articleStatus.getDisplay().equalsIgnoreCase(display)) {
                return articleStatus;
            }
        }
        return DRAFT;
    }

    public static ArticleStatus statusMatch(String status) {
        for (ArticleStatus articleStatus : ArticleStatus.values()) {
            if (articleStatus.getStatus().equalsIgnoreCase(status)) {
                return articleStatus;
            }
        }
        return DRAFT;
    }

}