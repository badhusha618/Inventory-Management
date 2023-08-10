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

import com.makinus.Inventory.common.data.dao.MobileUserRepository;
import com.makinus.Inventory.common.data.entity.User;
import com.makinus.Inventory.common.exception.InventoryException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Created by Bad_sha */
@Service
@Transactional
public class DashboardServiceImpl implements DashboardService {

  private final Logger LOG = LogManager.getLogger(DashboardServiceImpl.class);

  private final MobileUserRepository mobileUserRepository;

  public DashboardServiceImpl(@Autowired MobileUserRepository mobileUserRepository) {
    this.mobileUserRepository = mobileUserRepository;
  }

  @Override
  public List<User> usmUserList() throws InventoryException {
    LOG.info("List USM Mobile Users from database");
    return mobileUserRepository.findAllActiveUsers();
  }
}
