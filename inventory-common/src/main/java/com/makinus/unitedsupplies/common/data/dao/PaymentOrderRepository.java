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

import com.makinus.unitedsupplies.common.data.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author abuabdul
 */
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {

    @Query("select o from PaymentOrder o")
    List<PaymentOrder> listAllPaymentOrders();

    @Query("select o from PaymentOrder o where o.orderRef = :orderRef")
    Optional<PaymentOrder> findPaymentOrderByOrderRef(@Param("orderRef") Long orderRef);

    Optional<PaymentOrder> findById(Long id);
}
