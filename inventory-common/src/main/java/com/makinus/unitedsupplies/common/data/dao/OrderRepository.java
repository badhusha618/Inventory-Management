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

import com.makinus.unitedsupplies.common.data.entity.Order;
import com.makinus.unitedsupplies.common.data.service.Tuple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author Bad_sha
 */
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select o from Order o where o.deleted = 'F' and o.status != 'R' order by updatedDate desc")
    List<Order> listAllActiveOrders();

    @Query("select o from Order o where o.user.id = :userId and o.deleted = 'F' order by orderDate desc ")
    List<Order> listAllActiveOrdersByUserId(@Param("userId") Long userId);

    @Query("select o from Order o where o.orderRef = :orderRef and o.deleted = 'F'")
    Optional<Order> findOrderByOrderRef(@Param("orderRef") Long orderRef);

    @Query("select o from Order o where o.isRead = 'F' and o.deleted = 'F' order by updatedDate desc")
    List<Order> listNewOrders();

    @Query("select new com.makinus.unitedsupplies.common.data.service.Tuple(o.orderRef, count(po)) from Order o, ProductOrder po where po.orderRef = o.orderRef and o.deleted = 'F' group by o.orderRef")
    List<Tuple<Long, Integer>> getOrderProdOrderCount();

    @Query("select o from Order o")
    List<Order> listAllOrders();

    Optional<Order> findById(Long id);

    @Query("select o from Order o where o.custServInvoiceNo = :custServInvoiceNo and o.orderRef != :orderRef and o.deleted = 'F'")
    Order findCustomerInvoice(@Param("custServInvoiceNo") String custServInvoiceNo, @Param("orderRef") Long orderRef);

    @Query("select o from Order o where o.orderDate between :fromOrderDate and :toOrderDate and o.deleted = 'F'")
    List<Order> findOrdersByOrderDate(@Param("fromOrderDate") Date fromOrderDate, @Param("toOrderDate") Date toOrderDate);
}
