/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.service.excel;

import com.makinus.unitedsupplies.common.exception.UnitedSuppliesException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author hussain
 */
@FunctionalInterface
public interface GenericWriter<T> {

    void write(T object, HttpServletResponse response) throws UnitedSuppliesException;

}
