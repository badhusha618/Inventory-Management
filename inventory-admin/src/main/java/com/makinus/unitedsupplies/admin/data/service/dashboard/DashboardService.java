/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.service.dashboard;

import com.makinus.Inventory.common.data.entity.User;
import com.makinus.Inventory.common.exception.InventoryException;
import java.util.List;

/** Created by Bad_sha */
public interface DashboardService {

  List<User> usmUserList() throws InventoryException;
}
