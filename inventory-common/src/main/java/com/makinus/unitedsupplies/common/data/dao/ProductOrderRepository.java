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

import com.makinus.unitedsupplies.common.data.entity.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author ammar
 */
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Long> {

    Optional<ProductOrder> findById(Long id);

    @Query("select po from ProductOrder po where po.orderRef = :orderRef")
    List<ProductOrder> listAllProductsByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select po from ProductOrder po where po.orderRef = :orderRef and po.fulfillmentStatus = :fulfillmentStatus")
    List<ProductOrder> listProductsByOrderRefAndFulfillmentStatus(@Param("orderRef") Long orderRef, @Param("fulfillmentStatus") String fulfillmentStatus);

    @Query("select po from ProductOrder po where po.fulfillmentId = :fulfillmentId")
    List<ProductOrder> listProductsByFulfillmentId(@Param("fulfillmentId") Long fulfillmentId);

    @Query("from ProductOrder")
    List<ProductOrder> listAllProductOrders();

    @Query("select po from ProductOrder po where po.id in :ids")
    List<ProductOrder> listAllProductOrdersByIds(@Param("ids") List<Long> ids);

    @Query("select po from ProductOrder po where po.orderRef in :orderRefs")
    List<ProductOrder> listAllProductOrdersByOrderRefs(@Param("orderRefs") List<Long> orderRefs);


}
