/*
 *
 *  Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *  Unauthorized copying of this file, via any medium is strictly prohibited
 *  Proprietary and confidential
 *  Written by Makinus Pvt Ltd
 *
 */

package com.makinus.unitedsupplies.common.data.dao.filter.vendor;

import com.makinus.unitedsupplies.common.data.entity.Vendor;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.ParseException;
import java.util.Date;

import static com.makinus.unitedsupplies.common.utils.AppUtils.utcDateForDDMMYYYY;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * @author Bad_sha
 */
public class VendorFilterCriteriaBuilder {

    private CriteriaBuilder builder;
    private Predicate predicate;
    private Root<Vendor> vendorRoot;
    private boolean paramSet = Boolean.FALSE;

    private VendorFilterCriteriaBuilder(CriteriaBuilder builder, CriteriaQuery<Vendor> query) {
        this.builder = builder;
        this.predicate = builder.conjunction();
        this.vendorRoot = query.from(Vendor.class);
    }

    public static VendorFilterCriteriaBuilder aVendorFilterCriteria(
            CriteriaBuilder builder, CriteriaQuery<Vendor> query) {
        return new VendorFilterCriteriaBuilder(builder, query);
    }

    public VendorFilterCriteriaBuilder vendorName(String vendorName) {
        if (isNotEmpty(vendorName)) {
            predicate =
                    builder.and(
                            predicate, builder.like(vendorRoot.get("vendorName"), "%" + vendorName + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public VendorFilterCriteriaBuilder vendorCode(String vendorCode) {
        if (isNotEmpty(vendorCode)) {
            predicate =
                    builder.and(
                            predicate,
                            builder.like(vendorRoot.get("vendorCode"), "%" + vendorCode + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public VendorFilterCriteriaBuilder companyName(String companyName) {
        if (isNotEmpty(companyName)) {
            predicate =
                    builder.and(
                            predicate,
                            builder.like(vendorRoot.get("companyName"), "%" + companyName + "%"));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public Predicate predicate() {
        predicate =
                builder.and(predicate, builder.equal(vendorRoot.get("deleted"), YNStatus.NO.getStatus()));
        return this.predicate;
    }

    public VendorFilterCriteriaBuilder dateRange(String fromDate, String toDate) throws ParseException {
        if (isNotEmpty(fromDate) && isNotEmpty(toDate)) {
            Date from = utcDateForDDMMYYYY(fromDate);
            Date to = utcDateForDDMMYYYY(toDate);
            predicate = builder.and(predicate, builder.between(vendorRoot.get("createdDate"), from, to));
            paramSet = Boolean.TRUE;
        }
        return this;
    }

    public boolean isParamSet() {
        return this.paramSet;
    }

}
