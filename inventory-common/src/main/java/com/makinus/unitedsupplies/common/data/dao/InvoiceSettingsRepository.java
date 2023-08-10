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

import com.makinus.unitedsupplies.common.data.entity.InvoiceSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.List;

@Repository
public interface InvoiceSettingsRepository extends JpaRepository<InvoiceSettings, Long> {

    @Query("select c from InvoiceSettings c where c.sequenceType = :sequenceType")
    List<InvoiceSettings> findAllInvoiceSettingsByType(@Param("sequenceType") String sequenceType);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from InvoiceSettings c where c.sequenceType = :sequenceType")
    List<InvoiceSettings> findAllInvoiceSettingsByTypeWithLock(@Param("sequenceType") String sequenceType);
}
