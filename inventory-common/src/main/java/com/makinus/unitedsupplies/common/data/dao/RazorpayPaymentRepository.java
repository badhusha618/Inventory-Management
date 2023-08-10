/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.dao;

import com.makinus.unitedsupplies.common.data.entity.RazorpayPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface RazorpayPaymentRepository extends JpaRepository<RazorpayPayment, Long> {

    @Query("select r from RazorpayPayment r where r.orderId = :orderRef")
    Optional<RazorpayPayment> findRazorpayPaymentByOrderRef(@Param("orderRef") Long orderRef);

}
