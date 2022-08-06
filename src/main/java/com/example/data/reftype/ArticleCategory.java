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
public enum ArticleCategory {

    KNOW_YOUR_ISLAM("K", "Know Your Islam Landing Section"),
    Q_AND_A("Q", "Q & A"),
    JUMMA_SPEECH("J", "Jumma Speech");

    private String category;
    private String display;

    ArticleCategory(String category, String display) {
        this.category = category;
        this.display = display;
    }

    public String getCategory() {
        return category;
    }

    public String getDisplay() {
        return display;
    }

    public static ArticleCategory statusMatch(String category) {
        for (ArticleCategory status : ArticleCategory.values()) {
            if (status.getCategory().equalsIgnoreCase(category)) {
                return status;
            }
        }
        return KNOW_YOUR_ISLAM;
    }

}
