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

import com.makinus.unitedsupplies.admin.data.forms.TransportForm;
import com.makinus.unitedsupplies.common.data.entity.Transport;
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
 * Created by sabique
 */
@Component
@Qualifier("TransportMapper")
public class TransportMapper
        implements EntityMapper<TransportForm, List<Transport>>,
        EntityUpdateMapper<TransportForm, Transport>,
        EntityRemapper<Transport, TransportForm> {

    private final Logger LOG = LogManager.getLogger(TransportMapper.class);

    @Override
    public List<Transport> map(TransportForm transportForm) {
        LOG.info("Map Transport Form to Transport Entity");
        List<Transport> transports = new ArrayList<>();
        transportForm.getTranProductForms().forEach(t -> {
            if (StringUtils.isNotEmpty(transportForm.getTransGroup()) && StringUtils.isNotEmpty(t.getQuantity()) && StringUtils.isNotEmpty(t.getDistance()) && StringUtils.isNotEmpty(t.getCharges())) {
                Transport transport = new Transport();
                transport.setTransGroup(String.valueOf(transportForm.getTransGroup()));
                transport.setUnitId(Long.valueOf(transportForm.getUnitId()));
                transport.setQuantity(Integer.parseInt(t.getQuantity()));
                transport.setDistance(Integer.parseInt(t.getDistance()));
                transport.setCharges(new BigDecimal(t.getCharges()));
                transport.setCreatedBy(getCurrentUser());
                transport.setCreatedDate(getInstant());
                transport.setUpdatedBy(getCurrentUser());
                transport.setUpdatedDate(getInstant());
                transport.setDeleted(YNStatus.NO.getStatus());
                transport.setActive(YNStatus.YES.getStatus());
                transports.add(transport);
            }
        });
        return transports;
    }

    @Override
    public Transport map(TransportForm transportForm, Transport transport) {
        LOG.info("Map Transport Form to Updated Transport Entity");

        transport.setQuantity(Integer.parseInt(transportForm.getQuantity()));
        transport.setDistance(Integer.parseInt(transportForm.getDistance()));
        transport.setCharges(new BigDecimal(transportForm.getCharges()));
        transport.setUpdatedBy(getCurrentUser());
        transport.setUpdatedDate(getInstant());

        return transport;
    }

    @Override
    public TransportForm
    remap(Transport transport) {
        LOG.info("Map Transport Entity to Transport Form");
        TransportForm transportForm = new TransportForm();
        transportForm.setId(String.valueOf(transport.getId()));
        transportForm.setTransGroup(String.valueOf(transport.getTransGroup()));
        transportForm.setUnitId(String.valueOf(transport.getUnitId()));
        transportForm.setQuantity(String.valueOf(transport.getQuantity()));
        transportForm.setDistance(String.valueOf(transport.getDistance()));
        transportForm.setCharges(String.valueOf(transport.getCharges()));
        return transportForm;
    }
}
