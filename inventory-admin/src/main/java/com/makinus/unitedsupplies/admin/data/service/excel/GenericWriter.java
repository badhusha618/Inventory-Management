/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.service.excel;

import com.makinus.Inventory.common.exception.InventoryException;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Bad_sha
 */
@FunctionalInterface
public interface GenericWriter<T> {

    void write(T object, HttpServletResponse response) throws InventoryException;

}
