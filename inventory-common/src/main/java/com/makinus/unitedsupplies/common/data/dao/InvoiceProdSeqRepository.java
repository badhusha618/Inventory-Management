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

import com.makinus.unitedsupplies.common.data.entity.InvoiceProdSeq;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Bad_sha
 */
public interface InvoiceProdSeqRepository extends JpaRepository<InvoiceProdSeq, Long> {

    List<InvoiceProdSeq> findAllByVendorId(Long id) throws InventoryException;

}
