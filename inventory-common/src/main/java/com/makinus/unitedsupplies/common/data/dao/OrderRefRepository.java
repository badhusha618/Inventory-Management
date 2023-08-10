/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.OrderRef;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author abuabdul
 */
public interface OrderRefRepository extends JpaRepository<OrderRef, Long> {

}
