/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.admin.data.mapping;

import com.makinus.unitedsupplies.admin.data.forms.LoadingChargesForm;
import com.makinus.unitedsupplies.common.data.entity.LoadingCharges;
import com.makinus.unitedsupplies.common.data.mapper.EntityMapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityRemapper;
import com.makinus.unitedsupplies.common.data.mapper.EntityUpdateMapper;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;

/**
 * Created by abuabdul
 */
@Component
@Qualifier("LoadingChargesMapper")
public class LoadingChargesMapper
        implements EntityMapper<LoadingChargesForm, List<LoadingCharges>>,
        EntityUpdateMapper<LoadingChargesForm, LoadingCharges>,
        EntityRemapper<LoadingCharges, LoadingChargesForm> {

    private final Logger LOG = LogManager.getLogger(LoadingChargesMapper.class);

    @Override
    public List<LoadingCharges> map(LoadingChargesForm loadingChargesForm) {
        LOG.info("Map LoadingCharges Form to LoadingCharges Entity");
        List<LoadingCharges> loadingCharge = new ArrayList<>();
        loadingChargesForm.getLoadProductForm().forEach(t -> {
            if (StringUtils.isNotEmpty(loadingChargesForm.getProductId()) &&StringUtils.isNotEmpty(t.getQuantity()) && StringUtils.isNotEmpty(t.getCharges())) {
                LoadingCharges loadingCharges = new LoadingCharges();
                loadingCharges.setProductId(Long.valueOf(loadingChargesForm.getProductId()));
                loadingCharges.setQuantity(Integer.parseInt(t.getQuantity()));
                loadingCharges.setCharges(new BigDecimal(t.getCharges()));
                loadingCharges.setCreatedBy(getCurrentUser());
                loadingCharges.setCreatedDate(getInstant());
                loadingCharges.setUpdatedBy(getCurrentUser());
                loadingCharges.setUpdatedDate(getInstant());
                loadingCharges.setDeleted(YNStatus.NO.getStatus());
                loadingCharges.setActive(YNStatus.YES.getStatus());
                loadingCharge.add(loadingCharges);
            }
        });
        return loadingCharge;
    }

    @Override
    public LoadingCharges map(LoadingChargesForm loadingChargesForm, LoadingCharges loadingCharges) {
        LOG.info("Map LoadingCharges Form to Updated LoadingCharges Entity");

        loadingCharges.setProductId(Long.valueOf(loadingChargesForm.getProductId()));
        loadingCharges.setQuantity(Integer.parseInt(loadingChargesForm.getQuantity()));
        loadingCharges.setCharges(new BigDecimal(loadingChargesForm.getCharges()));
        loadingCharges.setUpdatedBy(getCurrentUser());
        loadingCharges.setUpdatedDate(getInstant());
        return loadingCharges;
    }

    @Override
    public LoadingChargesForm remap(LoadingCharges loadingCharges) {
        LOG.info("Map LoadingCharges Entity to LoadingCharges Form");
        LoadingChargesForm loadingChargesForm = new LoadingChargesForm();
        loadingChargesForm.setId(String.valueOf(loadingCharges.getId()));
        loadingChargesForm.setProductId(String.valueOf(loadingCharges.getProductId()));
        loadingChargesForm.setQuantity(String.valueOf(loadingCharges.getQuantity()));
        loadingChargesForm.setCharges(String.valueOf(loadingCharges.getCharges()));
        return loadingChargesForm;
    }
}
