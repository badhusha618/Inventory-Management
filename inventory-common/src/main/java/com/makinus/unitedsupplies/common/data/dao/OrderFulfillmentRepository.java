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

import com.makinus.unitedsupplies.common.data.entity.OrderFulfillment;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface OrderFulfillmentRepository extends JpaRepository<OrderFulfillment, Long> {

    Optional<OrderFulfillment> findById(Long id);

    @Query(value = "SELECT FULFILLMENT_REF FROM ORDER_FULFILLMENT WHERE ORDER_REF = :orderRef ORDER BY ID DESC LIMIT 1", nativeQuery = true)
    Optional<String> findLastFulfillmentRefByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from OrderFulfillment o where o.orderRef = :orderRef")
    List<OrderFulfillment> listAllFulfillmentByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from OrderFulfillment o where o.orderRef = :orderRef and o.productInvoiceNo is not null")
    List<OrderFulfillment> listGeneratedInvoicesByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from OrderFulfillment o where o.fulfillmentRef = :fulfillmentRef")
    Optional<OrderFulfillment> findFulfillmentByFulfillmentRef(@Param("fulfillmentRef") String fulfillmentRef);

    @Query("select new com.makinus.unitedsupplies.common.data.service.Tuple(o.id, o.fulfillmentRef) from OrderFulfillment o where o.orderRef = :orderRef and o.prodVendorId is not null")
    List<Tuple<Long, String>> listFulfillmentRefByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from OrderFulfillment o where o.orderRef = :orderRef and o.prodVendorId is not null")
    List<OrderFulfillment> listFulfillmentByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from OrderFulfillment o where o.id in :fulfillmentIds")
    List<OrderFulfillment> listFulfillmentByFulfillmentIds(@Param("fulfillmentIds") List<Long> fulfillmentIds);

    @Query("select o from OrderFulfillment o where o.productInvoiceNo = :productInvoiceNo and o.id != :fulfillmentId")
    OrderFulfillment findProductInvoice(@Param("productInvoiceNo") String productInvoiceNo, @Param("fulfillmentId") Long fulfillmentId);

    @Query("select o from OrderFulfillment o where o.sellServInvoiceNo = :sellServInvoiceNo and o.id != :fulfillmentId")
    OrderFulfillment findServiceInvoice(@Param("sellServInvoiceNo") String sellServInvoiceNo, @Param("fulfillmentId") Long fulfillmentId);

    @Query("select o from OrderFulfillment o")
    List<OrderFulfillment> listAllFulfillment();

}
