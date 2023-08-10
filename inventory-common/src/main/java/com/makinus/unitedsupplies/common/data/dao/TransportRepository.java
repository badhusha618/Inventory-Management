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

import com.makinus.unitedsupplies.common.data.entity.Transport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * @author sabique
 */
public interface TransportRepository extends JpaRepository<Transport, Long> {

    @Query("select t from Transport t where (t.quantity != 0 or t.distance != 0 or t.charges != 0) and t.deleted = 'F' order by updatedDate desc")
    List<Transport> listAllTransport();

    @Query("select t from Transport t where t.deleted = 'F' and t.transGroup = :transGroup")
    List<Transport> listAllTransportsByTransGroup(@Param("transGroup") String transGroup);

    @Query("select t from Transport t where t.quantity = :quantity and t.distance = :distance and t.transGroup = :transGroup and t.deleted = 'F'")
    Optional<Transport> findAvailableQuantityAndDistanceForProduct(@Param("quantity") Integer quantity, @Param("distance") Integer distance, @Param("transGroup") String transGroup);

    @Query("select t from Transport t where t.quantity = 0 and t.distance = 0 and t.charges = 0")
    List<Transport> listTransportGroup();

    Optional<Transport> findById(Long id);

    @Query("select t from Transport t where t.deleted = 'F' and t.transGroup in :transGroups")
    List<Transport> listAllTransportsByTransGroups(@Param("transGroups") List<String> transGroups);

}