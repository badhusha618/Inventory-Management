/*
 *
 *    Copyright (c) 2017 Makinus Pvt Ltd - All Rights Reserved
 *
 *    Unauthorized copying of this file, via any medium is strictly prohibited
 *    Proprietary and confidential
 *    Written by Makinus Pvt Ltd
 *
 */
package com.makinus.unitedsupplies.common.data.service.transport;

import com.makinus.unitedsupplies.common.data.dao.TransportRepository;
import com.makinus.unitedsupplies.common.data.entity.Transport;
import com.makinus.unitedsupplies.common.data.reftype.YNStatus;
import com.makinus.unitedsupplies.common.exception.InventoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.makinus.unitedsupplies.common.utils.AppUtils.getCurrentUser;
import static com.makinus.unitedsupplies.common.utils.AppUtils.getInstant;
import static java.lang.String.format;

/**
 * @author Bad_sha
 */
@Service
@Transactional
public class TransportServiceImpl implements TransportService {

    private final Logger LOG = LogManager.getLogger(TransportServiceImpl.class);

    private final TransportRepository transportRepository;

    public TransportServiceImpl(@Autowired TransportRepository transportRepository) {
        this.transportRepository = transportRepository;
    }

    @Override
    public boolean isQuantityAndDistanceExists(Integer quantity, Integer distance, String transGroup) {
        LOG.info("Check if Transport Charge is available from database");
        Optional<Transport> findTransportCharges = transportRepository.findAvailableQuantityAndDistanceForProduct(quantity, distance, transGroup);
        return findTransportCharges.isPresent();
    }

    @Override
    public List<Transport> transportList() {
        LOG.info("List all transport from database");
        return transportRepository.listAllTransport();
    }

    @Override
    public List<Transport> transportListByTransGroups(List<String> transGroups) {
        LOG.info("List all Transport by list of transport group from database");
        return transportRepository.listAllTransportsByTransGroups(transGroups);
    }

    @Override
    public List<Transport> transportListByTransGroup(String transGroup) {
        LOG.info("List all Transport by transport group from database");
        return transportRepository.listAllTransportsByTransGroup(transGroup);
    }

    @Override
    public List<Transport> addNewTransports(List<Transport> transports) {
        LOG.info("List all Transport by Product Ids from database");
        return transportRepository.saveAll(transports);
    }

    @Override
    public Transport addNewTransport(Transport transport) {
        LOG.info("Add New Transport in the catalog");
        Transport savedTransport = transportRepository.save(transport);
        LOG.info("Saved New Transport in the database");
        return savedTransport;
    }

    @Override
    public Transport findTransport(Long id) throws InventoryException {
        Optional<Transport> transportOptional = transportRepository.findById(id);
        if (transportOptional.isPresent()) {
            Transport transport = transportOptional.get();
            return transport;
        }
        throw new InventoryException(format("transport is not found with the id %d", id));
    }

    @Override
    public Transport updateTransport(Transport transport) {
        LOG.info("Update existing transport in the catalog");
        return transportRepository.save(transport);
    }

    @Override
    public Transport removeTransport(Long id) throws InventoryException {
        Optional<Transport> transportOptional = transportRepository.findById(id);
        if (transportOptional.isPresent()) {
            Transport transport = transportOptional.get();
            transport.setDeleted(YNStatus.YES.getStatus());
            transport.setUpdatedBy(getCurrentUser());
            transport.setUpdatedDate(getInstant());
            return transport;
        }
        throw new InventoryException(format("Transport is not found with the id %s", id));
    }

    @Override
    public List<Transport> transportGroupList() {
        LOG.info("List all transport group list  from database");
        return transportRepository.listTransportGroup();
    }

}
