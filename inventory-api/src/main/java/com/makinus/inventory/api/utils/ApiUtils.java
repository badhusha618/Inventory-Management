/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.inventory.api.utils;

import com.makinus.unitedsupplies.common.data.entity.Product;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bad_sha
 */
public interface ApiUtils {

    String SHOW_MORE_TEXT = "more";
    String DEFAULT_DELIVERY_PINCODE = "627012";

    String INDIAN_CURRENCY = "INR";
    int AUTO_CAPTURE = 1;

    String SIGNATURE_VERIFICATION_DESCRIPTION = "Signature ({0}) verification has been {1} with payment id ({2}) and order id ({3}) for order ({4}) - On step {5}.";

    static List<String> imagePaths(Product product) {
        List<String> imagePaths = new ArrayList<>();
        imagePaths.add((product.getImagePath()));
        if (StringUtils.isNotEmpty(product.getOptImagePath1())) {
            imagePaths.add((product.getOptImagePath1()));
        }
        if (StringUtils.isNotEmpty(product.getOptImagePath2())) {
            imagePaths.add((product.getOptImagePath2()));
        }
        if (StringUtils.isNotEmpty(product.getOptImagePath3())) {
            imagePaths.add((product.getOptImagePath3()));
        }
        return imagePaths;
    }

    static List<String> mapQuantityList(Integer minOrderQuantity) {
        List<String> quantityList = new ArrayList<>();
        Integer inc = minOrderQuantity < 50 ? 1 : minOrderQuantity < 100 ? 5 : minOrderQuantity < 500 ? 10 : 50;
        for (int i = 0; i <= 4; i++) {
            quantityList.add(String.valueOf(minOrderQuantity));
            minOrderQuantity += inc;
        }
        quantityList.add(ApiUtils.SHOW_MORE_TEXT);
        return quantityList;
    }

    static int valueInPaise(BigDecimal value) {
        return value.multiply(new BigDecimal(100)).intValue();
    }
}
