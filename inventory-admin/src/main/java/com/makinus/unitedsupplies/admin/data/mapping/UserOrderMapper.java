/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.Inventory.admin.data.mapping;

import static com.makinus.Inventory.common.utils.AppUtils.getInstant;

import com.makinus.Inventory.admin.controller.api.json.request.UserOrder;
import com.makinus.Inventory.common.data.entity.Order;
import com.makinus.Inventory.common.data.entity.User;
import com.makinus.Inventory.common.data.mapper.EntityWithExtraValueMapper;
import com.makinus.Inventory.common.data.reftype.ProdOrderStatus;
import com.makinus.Inventory.common.data.reftype.YNStatus;
import java.math.BigDecimal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/** Created by Bad_sha */
@Component
@Qualifier("UserOrderMapper")
public class UserOrderMapper implements EntityWithExtraValueMapper<UserOrder, User, Order> {

  private final Logger LOG = LogManager.getLogger(UserOrderMapper.class);

  @Override
  public Order mapExtra(UserOrder userOrder, final User mobileUser) {
    LOG.info("Map New User Order to Order Entity");
    Order order = new Order();
    order.setUser(mobileUser);
    order.setOrderDate(getInstant());
    order.setOrderTotal(new BigDecimal(userOrder.getOrderTotal()));
    order.setStatus(ProdOrderStatus.NEW.getStatus());
    order.setDeleted(YNStatus.NO.getStatus());
    return order;
  }
}
